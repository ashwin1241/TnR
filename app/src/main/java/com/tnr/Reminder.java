package com.tnr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Reminder extends AppCompatActivity {

    private ArrayList<Reminder_card_data> remlList;
    private RecyclerView rRecyclerView;
    private Reminder_Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    private ImageButton add_rem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Reminders");

        loadData();
        buildRecyclerView();

        add_rem = findViewById(R.id.add_reminder);
        add_rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_reminder_card(0);
            }
        });

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
            case android.R.id.home: startActivity(new Intent(Reminder.this,Dashboard.class));
            break;
        }
        return true;
    }

    private void buildRecyclerView()
    {
        rRecyclerView = findViewById(R.id.reminder_rec_view);
        rLayoutManager = new LinearLayoutManager(this);
        rAdapter = new Reminder_Adapter(remlList, Reminder.this);
        rRecyclerView.setHasFixedSize(true);
        rRecyclerView.setLayoutManager(rLayoutManager);
        rRecyclerView.setAdapter(rAdapter);

        rAdapter.setOnItemClickListener(new Reminder_Adapter.OnItemClickListener() {
            @Override
            public void OnItemClicked(int position) {
                Intent intent = new Intent(Reminder.this,Reminder_Preview.class);
                intent.putExtra("card_id",remlList.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void OnDeleteClicked(int position) {
                delete_reminder_card(position);
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

    private void add_reminder_card(int position)
    {
        Intent intent = new Intent(Reminder.this,Add_Reminder.class);
        startActivity(intent);
    }

    private void delete_reminder_card(int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete reminder")
        .setMessage("Are you sure you want to delete this reminder?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                remlList.remove(position);
                rAdapter.notifyItemRemoved(position);
                saveData();
                Toast.makeText(Reminder.this, "Reminder deleted", Toast.LENGTH_SHORT).show();
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}