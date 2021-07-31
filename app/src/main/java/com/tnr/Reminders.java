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

public class Reminders extends AppCompatActivity {

    private ArrayList<Reminders_Card_Data> remlList;
    private RecyclerView rRecyclerView;
    private Reminders_Adapter rAdapter;
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

    private void buildRecyclerView()
    {
        rRecyclerView = findViewById(R.id.reminder_rec_view);
        rLayoutManager = new LinearLayoutManager(this);
        rAdapter = new Reminders_Adapter(remlList, Reminders.this);
        rRecyclerView.setHasFixedSize(true);
        rRecyclerView.setLayoutManager(rLayoutManager);
        rRecyclerView.setAdapter(rAdapter);

        rAdapter.setOnItemClickListener(new Reminders_Adapter.OnReminderItemClickListener() {
            @Override
            public void OnItemClicked(int position) {
                Intent intent = new Intent(Reminders.this, Reminders_Preview.class);
                intent.putExtra("card_id",remlList.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void OnDeleteClicked(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Reminders.this);
                builder.setTitle("Delete reminder")
                        .setMessage("Are you sure you want to delete this reminder?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                remlList.remove(position);
                                rAdapter.notifyItemRemoved(position);
                                saveData();
                                Toast.makeText(Reminders.this, "Reminder deleted", Toast.LENGTH_SHORT).show();
                                buildRecyclerView();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();
            }
        });

    }

    private void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("reminder_activity_sp",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("rem_list",null);
        Type type = new TypeToken<ArrayList<Reminders_Card_Data>>(){}.getType();
        remlList = gson.fromJson(json,type);
        if(remlList==null)
        {
            remlList = new ArrayList<Reminders_Card_Data>();
        }
    }

    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("reminder_activity_sp",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(remlList);
        editor.putString("rem_list",json);
        editor.apply();
    }

    private void add_reminder_card(int position)
    {
        Intent intent = new Intent(Reminders.this,Add_Reminder.class);
        startActivity(intent);
    }

    private void delete_reminder_card(int position)
    {

    }

}