package com.tnr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Lists extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Lists");
    }
}