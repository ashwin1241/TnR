package com.tnr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Tasks_Preview extends AppCompatActivity {

    private long card_id;
    private int position;
    private List<Tasks_Card_Data> tskList;
    private TextView tsk_prev_title;
    private TextView tsk_prev_description;
    private TextView tsk_prev_date_and_time;
    private ExtendedFloatingActionButton edit_tsk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_preview);
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

        tsk_prev_title = findViewById(R.id.task_prev_title);
        tsk_prev_description = findViewById(R.id.task_prev_description);
        tsk_prev_date_and_time = findViewById(R.id.task_prev_date_and_time);
        edit_tsk = findViewById(R.id.task_prev_edit);
        edit_tsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tasks_Preview.this,Tasks_Edit.class);
                intent.putExtra("card_id",tskList.get(position).getId());
                startActivity(intent);
            }
        });

        tsk_prev_title.setText(tskList.get(position).getTitle());
        tsk_prev_description.setText(tskList.get(position).getDescription());
        tsk_prev_date_and_time.setText(tskList.get(position).getDate()+"\n"+tskList.get(position).getTime());

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

}