package com.tnr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class Tasks_Preview extends AppCompatActivity {

    private TextView tsk_prev_title;
    private TextView tsk_prev_description;
    private TextView tsk_prev_date_and_time;
    private ExtendedFloatingActionButton edit_tsk;
    private Tasks_Card_Data card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_preview);
        getSupportActionBar().setHomeButtonEnabled(true);

        card = (Tasks_Card_Data) getIntent().getSerializableExtra("card");

        getSupportActionBar().setTitle(card.getTitle());

        tsk_prev_title = findViewById(R.id.task_prev_title);
        tsk_prev_description = findViewById(R.id.task_prev_description);
        tsk_prev_date_and_time = findViewById(R.id.task_prev_date_and_time);
        edit_tsk = findViewById(R.id.task_prev_edit);
        edit_tsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tasks_Preview.this,Tasks_Edit.class);
                intent.putExtra("card",card);
                startActivity(intent);
            }
        });

        tsk_prev_title.setText(card.getTitle());
        tsk_prev_description.setText(card.getDescription());
        tsk_prev_date_and_time.setText(card.getDate()+"\n"+card.getTime());

    }

}