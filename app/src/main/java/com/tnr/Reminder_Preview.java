package com.tnr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Reminder_Preview extends AppCompatActivity {

    private List<Reminder_card_data> remlList;
    private int position;
    private long card_id;
    private EditText prev_title;
    private TextView prev_date;
    private TextView prev_time;
    private EditText time_offset;
    private EditText prev_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_preview);
        getSupportActionBar().setHomeButtonEnabled(true);

        loadData();

        card_id = getIntent().getLongExtra("card_id",0);
        for(int i=0;i<remlList.size();i++)
        {
            if(remlList.get(i).getId()==card_id)
            {
                position=i;
                break;
            }
        }

        getSupportActionBar().setTitle(remlList.get(position).getTitle());

        prev_title = findViewById(R.id.prev_title);
        prev_date = findViewById(R.id.prev_date);
        prev_time = findViewById(R.id.prev_time);
        time_offset = findViewById(R.id.time_offset_for_rem);
        prev_desc = findViewById(R.id.prev_desc);

        prev_title.setText(remlList.get(position).getTitle());
        prev_date.setText(remlList.get(position).getDate());
        prev_time.setText(remlList.get(position).getTime());
        time_offset.setText("0");
        prev_desc.setText(remlList.get(position).getDescription());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.reminder_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.save_edited_rem: save_rem_edits();
                break;
            case android.R.id.home: startActivity(new Intent(Reminder_Preview.this, Reminder.class));
                break;
        }
        return true;
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

    private void save_rem_edits()
    {
        saveData();
        Toast.makeText(this, "Edits saved", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Reminder_Preview.this,Reminder.class);
        startActivity(intent);
        finish();
    }

}