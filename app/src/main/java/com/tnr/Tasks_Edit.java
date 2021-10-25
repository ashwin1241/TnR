package com.tnr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Tasks_Edit extends AppCompatActivity {

    private EditText tsk_edit_title;
    private EditText tsk_edit_description;
    private Tasks_Databse databse;
    private Task_Dao dao;
    private Tasks_Card_Data card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_edit);
        getSupportActionBar().setHomeButtonEnabled(true);

        class loadData extends AsyncTask<Void,Void,Void>
        {
            @Override
            protected Void doInBackground(Void... voids) {
                databse = Room.databaseBuilder(Tasks_Edit.this,Tasks_Databse.class,"Tasks").build();
                dao = databse.task_dao();
                return null;
            }
            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
            }
        }
        new loadData().execute();

        card = (Tasks_Card_Data) getIntent().getSerializableExtra("card");

        getSupportActionBar().setTitle(card.getTitle());

        tsk_edit_title = findViewById(R.id.task_edit_title);
        tsk_edit_description = findViewById(R.id.task_edit_description);

        tsk_edit_title.setText(card.getTitle());
        tsk_edit_description.setText(card.getDescription());

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
                                    intent.putExtra("card",card);
                                    startActivity(intent);
                break;
        }
        return true;
    }

    private void save_tsk_edits()
    {
        class UpdateData extends AsyncTask<Void,Void,Tasks_Card_Data>
        {
            @Override
            protected void onPostExecute(Tasks_Card_Data card) {
                super.onPostExecute(card);
                Toast.makeText(Tasks_Edit.this, "Edits saved", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Tasks_Edit.this, Tasks_Preview.class);
                intent.putExtra("card",(Serializable) card);
                startActivity(intent);
                finish();
            }
            @Override
            protected Tasks_Card_Data doInBackground(Void... voids) {
                card.setTitle(tsk_edit_title.getText().toString().trim());
                card.setDescription(tsk_edit_description.getText().toString().trim());
                dao.update(card);
                long id = card.getId();
                card = dao.getCard(id);
                return card;
            }
        }
        new UpdateData().execute();
    }

}