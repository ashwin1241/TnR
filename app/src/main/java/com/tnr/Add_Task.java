package com.tnr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Add_Task extends AppCompatActivity {

    private List<Tasks_Card_Data> tskList;
    private long card_id;
    private int position;
    private EditText tsk_edit_title;
    private EditText tsk_edit_description;
    private String tsk_time;
    private String tsk_date;
    private ExtendedFloatingActionButton saveChanges1;
    private ExtendedFloatingActionButton discardChanges1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

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

        getSupportActionBar().setTitle("Add a task");

        tsk_edit_title = findViewById(R.id.task_add_title);
        tsk_edit_description = findViewById(R.id.task_add_description);
        saveChanges1 = findViewById(R.id.save_changes1);
        saveChanges1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_tsk_edits();
            }
        });
        discardChanges1 = findViewById(R.id.discard_changes1);
        discardChanges1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Add_Task.this);
                builder.setTitle("Discard changes")
                        .setMessage("Are you sure you want to discard changes?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Add_Task.this, Tasks.class);
                                startActivity(intent);
                            }
                        })
                ;
                builder.create().show();
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

    private void save_tsk_edits()
    {
        tsk_time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        tsk_date="";
        switch (month)
        {
            case 0: tsk_date+="Jan";
                break;
            case 1: tsk_date+="Feb";
                break;
            case 2: tsk_date+="Mar";
                break;
            case 3: tsk_date+="Apr";
                break;
            case 4: tsk_date+="May";
                break;
            case 5: tsk_date+="Jun";
                break;
            case 6: tsk_date+="Jul";
                break;
            case 7: tsk_date+="Aug";
                break;
            case 8: tsk_date+="Sept";
                break;
            case 9: tsk_date+="Oct";
                break;
            case 10: tsk_date+="Nov";
                break;
            case 11: tsk_date+="Dec";
                break;
        }
        tsk_date+=" "+Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+", "+Calendar.getInstance().get(Calendar.YEAR);
        tskList.add(tskList.size(),new Tasks_Card_Data(System.currentTimeMillis(),tsk_edit_title.getText().toString().trim(),tsk_edit_description.getText().toString().trim(),tsk_date,tsk_time));
        saveData();
        Toast.makeText(Add_Task.this, "Task added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Add_Task.this, Tasks.class);
        startActivity(intent);
        finish();
    }

}