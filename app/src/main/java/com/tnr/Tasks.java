package com.tnr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Tasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Tasks");
    }
}