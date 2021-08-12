package com.tnr;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class Custom_Frequency_Setter extends AppCompatActivity {

    ExtendedFloatingActionButton sunday;
    ExtendedFloatingActionButton monday;
    ExtendedFloatingActionButton tuesday;
    ExtendedFloatingActionButton wednesday;
    ExtendedFloatingActionButton thursday;
    ExtendedFloatingActionButton friday;
    ExtendedFloatingActionButton saturday;
    ExtendedFloatingActionButton custom_done;
    TextView end_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_frequency_setter);
        getSupportActionBar().setTitle("Custom recurrence");

        sunday = findViewById(R.id.Sunday_button);
        monday = findViewById(R.id.Monday_button);
        tuesday = findViewById(R.id.Tuesday_button);
        wednesday = findViewById(R.id.Wednesday_button);
        thursday = findViewById(R.id.Thursday_button);
        friday = findViewById(R.id.Friday_button);
        saturday = findViewById(R.id.Saturday_button);
        custom_done = findViewById(R.id.custom_done);
        end_date = findViewById(R.id.end_date);

        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sunday.setBackgroundColor(Color.parseColor("#0000FF"));
            }
        });
        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monday.setBackgroundColor(Color.parseColor("#0000FF"));
            }
        });
        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuesday.setBackgroundColor(Color.parseColor("#0000FF"));
            }
        });
        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wednesday.setBackgroundColor(Color.parseColor("#0000FF"));
            }
        });
        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thursday.setBackgroundColor(Color.parseColor("#0000FF"));
            }
        });
        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friday.setBackgroundColor(Color.parseColor("#0000FF"));
            }
        });
        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saturday.setBackgroundColor(Color.parseColor("#0000FF"));
            }
        });

    }
}