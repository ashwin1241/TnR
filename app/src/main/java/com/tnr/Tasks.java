package com.tnr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class Tasks extends AppCompatActivity {

    private List<Tasks_Card_Data> tskList;
    private RecyclerView tRecyclerView;
    private Tasks_Adapter tAdapter;
    private RecyclerView.LayoutManager tLayoutManager;
    private ImageButton add_tsk;
    private App_Databse databse;
    private Task_Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Tasks");

        class loadData extends AsyncTask<Void,Void,Void>
        {
            @Override
            protected Void doInBackground(Void... voids) {
                databse = Room.databaseBuilder(Tasks.this, App_Databse.class,"TnR_App_Database").build();
                dao = databse.task_dao();
                tskList = dao.getAll();
                return null;
            }
            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                buildRecyclerView();
            }
        }
        new loadData().execute();

        add_tsk = findViewById(R.id.add_task);
        add_tsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tasks.this,Add_Task.class);
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
                intent.putExtra("card",tskList.get(position));
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
                        class DeleteTask extends AsyncTask<Void,Void,Void>
                        {
                            @Override
                            protected void onPostExecute(Void unused) {
                                super.onPostExecute(unused);
                                buildRecyclerView();
                                Toast.makeText(Tasks.this, "Task deleted", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            protected Void doInBackground(Void... voids) {
                                dao.delete(tskList.get(position));
                                tskList = dao.getAll();
                                return null;
                            }
                        }
                        new DeleteTask().execute();
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

}