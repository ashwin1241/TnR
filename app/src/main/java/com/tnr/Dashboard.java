package com.tnr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Dashboard extends AppCompatActivity {

    private ImageButton reminders;
    private ImageButton tasks;
    private ImageButton lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        reminders = findViewById(R.id.reminders);
        tasks = findViewById(R.id.tasks);
        lists = findViewById(R.id.lists);

        reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Reminders.class);
                startActivity(intent);
            }
        });
        tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Tasks.class);
                startActivity(intent);
            }
        });
        lists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,Lists.class);
                startActivity(intent);
            }
        });

    }
}