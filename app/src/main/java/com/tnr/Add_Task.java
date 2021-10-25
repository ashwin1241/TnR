package com.tnr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Calendar;

public class Add_Task extends AppCompatActivity {

    private EditText tsk_edit_title;
    private EditText tsk_edit_description;
    private String tsk_time;
    private String tsk_date;
    private ExtendedFloatingActionButton saveChanges1;
    private ExtendedFloatingActionButton discardChanges1;
    private Tasks_Databse databse;
    private Task_Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        class loadData extends AsyncTask<Void,Void,Void>
        {
            @Override
            protected Void doInBackground(Void... voids) {
                databse = Room.databaseBuilder(Add_Task.this,Tasks_Databse.class,"Tasks").build();
                dao = databse.task_dao();
                return null;
            }
            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
            }
        }
        new loadData().execute();

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
        class AddTask extends AsyncTask<Void,Void,Void>
        {
            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(Add_Task.this, "Task added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Add_Task.this, Tasks.class);
                startActivity(intent);
                finish();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                dao.insert(new Tasks_Card_Data(System.currentTimeMillis(),tsk_edit_title.getText().toString().trim(),tsk_edit_description.getText().toString().trim(),tsk_date,tsk_time));
                return null;
            }
        }
        new AddTask().execute();
    }

}