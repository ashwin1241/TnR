package com.tnr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Reminders extends AppCompatActivity {

    private ArrayList<Reminder_card_data> remlList;
    private RecyclerView rRecyclerView;
    private Reminder_Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Reminders");

        loadData();
        buildRecyclerView();

        remlList.add(new Reminder_card_data("Title_1",remlList.size()+1));
        remlList.add(new Reminder_card_data("Title_2",remlList.size()+1));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.reminder_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.search:
            break;
            case android.R.id.home: startActivity(new Intent(Reminders.this,Dashboard.class));
            break;
        }
        return true;
    }

    private void buildRecyclerView()
    {
        rRecyclerView = findViewById(R.id.reminder_rec_view);
        rLayoutManager = new LinearLayoutManager(this);
        rAdapter = new Reminder_Adapter(remlList,Reminders.this);
        rRecyclerView.setHasFixedSize(true);
        rRecyclerView.setLayoutManager(rLayoutManager);
        rRecyclerView.setAdapter(rAdapter);

        rAdapter.setOnItemClickListener(new Reminder_Adapter.OnItemClickListener() {
            @Override
            public void OnItemClicked(int position) {

            }

            @Override
            public void OnDeleteClicked(int position) {

            }
        });

    }

    private void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("reminder_activity_sp",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("doc_list",null);
        Type type = new TypeToken<ArrayList<Reminder_card_data>>(){}.getType();
        remlList = gson.fromJson(json,type);
        if(remlList==null)
        {
            remlList = new ArrayList<Reminder_card_data>();
        }
    }

    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("reminder_activity_sp",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(remlList);
        editor.putString("doc_list",json);
        editor.apply();
    }
}