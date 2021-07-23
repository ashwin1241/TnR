package com.tnr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Reminder_Preview extends AppCompatActivity {

    private List<Reminder_card_data> remlList;
    private int position;
    private long card_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_preview);
        getSupportActionBar().setHomeButtonEnabled(true);

        loadData();

        position = getIntent().getIntExtra("position",0);
        card_id = remlList.get(position).getId();
        getSupportActionBar().setTitle(remlList.get(position).getTitle());

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