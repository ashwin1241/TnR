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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Tasks_Edit extends AppCompatActivity {

    private List<Tasks_Card_Data> tskList;
    private long card_id;
    private int position;
    private EditText tsk_edit_title;
    private EditText tsk_edit_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_edit);
        getSupportActionBar().setHomeButtonEnabled(true);

        loadData();

        card_id = getIntent().getLongExtra("card_id",0);
        for(int i=0;i<tskList.size();i++)
        {
            if(tskList.get(i).getId()==card_id)
            {
                position = i;
                break;
            }
        }

        getSupportActionBar().setTitle(tskList.get(position).getTitle());

        tsk_edit_title = findViewById(R.id.task_edit_title);
        tsk_edit_description = findViewById(R.id.task_edit_description);

        tsk_edit_title.setText(tskList.get(position).getTitle());
        tsk_edit_description.setText(tskList.get(position).getDescription());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.save_edited_rem: save_tsk_edits();
                break;
            case android.R.id.home: Intent intent = new Intent(Tasks_Edit.this, Tasks_Preview.class);
                                    intent.putExtra("card_id",tskList.get(position).getId());
                                    startActivity(intent);
                break;
        }
        return true;
    }

    private void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("task_activity_sp",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("tsk_list",null);
        Type type = new TypeToken<ArrayList<Tasks_Card_Data>>(){}.getType();
        tskList = gson.fromJson(json,type);
        if(tskList==null)
        {
            tskList = new ArrayList<Tasks_Card_Data>();
        }
    }

    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("task_activity_sp",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(tskList);
        editor.putString("tsk_list",json);
        editor.apply();
    }

    private void save_tsk_edits()
    {
        tskList.get(position).setTitle(tsk_edit_title.getText().toString().trim());
        tskList.get(position).setDescription(tsk_edit_description.getText().toString().trim());
        saveData();
        Toast.makeText(this, "Edits saved", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Tasks_Edit.this, Tasks_Preview.class);
        intent.putExtra("card_id",tskList.get(position).getId());
        startActivity(intent);
        finish();
    }

}