package com.tnr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Tasks extends AppCompatActivity {

    private ArrayList<Tasks_Card_Data> tskList;
    private RecyclerView tRecyclerView;
    private Tasks_Adapter tAdapter;
    private RecyclerView.LayoutManager tLayoutManager;
    private ImageButton add_tsk;
    private String tsk_date;
    private String tsk_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Tasks");

        loadData();
        buildRecyclerView();

        add_tsk = findViewById(R.id.add_task);
        add_tsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tasks.this,Add_Task.class);
                intent.putExtra("card_id",System.currentTimeMillis());
                startActivity(intent);
            }
        });
    }

    private void buildRecyclerView()
    {
        tRecyclerView = findViewById(R.id.task_rec_view);
        tLayoutManager = new GridLayoutManager(this,2);
        tAdapter = new Tasks_Adapter(tskList,Tasks.this);
        tRecyclerView.setHasFixedSize(true);
        tRecyclerView.setLayoutManager(tLayoutManager);
        tRecyclerView.setAdapter(tAdapter);

        tAdapter.setOnItemClickListener(new Tasks_Adapter.OnTaskItemClickListener() {
            @Override
            public void OnItemClicked(int position) {
                Intent intent = new Intent(Tasks.this,Tasks_Preview.class);
                intent.putExtra("card_id",tskList.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void OnItemDeleted(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Tasks.this);
                builder.setTitle("Delete task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tskList.remove(position);
                        tAdapter.notifyItemRemoved(position);
                        saveData();
                        Toast.makeText(Tasks.this, "Task deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }

            @Override
            public void OnItemShared(int position) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,tskList.get(position).getTitle());
                intent.putExtra(Intent.EXTRA_TEXT,tskList.get(position).getTitle()+"\n\n"+tskList.get(position).getDescription());
                startActivity(Intent.createChooser(intent,"Share note with.."));
            }
        });

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

}