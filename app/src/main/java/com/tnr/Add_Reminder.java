package com.tnr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Add_Reminder extends AppCompatActivity {

    private List<Reminders_Card_Data> remlList;
    private TextView date;
    private TextView time;
    private TextView rem_frequency;
    private ExtendedFloatingActionButton date_choose;
    private ExtendedFloatingActionButton time_choose;
    private ExtendedFloatingActionButton save_changes;
    private ExtendedFloatingActionButton discard_changes;
    private ExtendedFloatingActionButton rem_frequency_chooser;
    private EditText description;
    private EditText title;
    private String rem_time = null;
    private String rem_date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        getSupportActionBar().setTitle("Add a reminder");

        loadData();

        date = findViewById(R.id.selected_date);
        time = findViewById(R.id.selected_time);
        date_choose = findViewById(R.id.date_chooser);
        time_choose = findViewById(R.id.time_chooser);
        save_changes = findViewById(R.id.save_changes);
        discard_changes = findViewById(R.id.discard_changes);
        description = findViewById(R.id.rem_desc);
        title = findViewById(R.id.rem_title);
        rem_frequency = findViewById(R.id.rem_freq);
        rem_frequency_chooser = findViewById(R.id.rem_freq_chooser);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        discard_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Add_Reminder.this);
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
                        Intent intent = new Intent(Add_Reminder.this, Reminders.class);
                        startActivity(intent);
                        finish();
                    }
                })
                ;
                builder.create().show();
            }
        });

        date_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Reminder.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        rem_date = day+"/"+month+"/"+year;
                        String tempdate="";
                        switch (month)
                        {
                            case 1: tempdate+="Jan";
                                break;
                            case 2: tempdate+="Feb";
                                break;
                            case 3: tempdate+="Mar";
                                break;
                            case 4: tempdate+="Apr";
                                break;
                            case 5: tempdate+="May";
                                break;
                            case 6: tempdate+="Jun";
                                break;
                            case 7: tempdate+="Jul";
                                break;
                            case 8: tempdate+="Aug";
                                break;
                            case 9: tempdate+="Sept";
                                break;
                            case 10: tempdate+="Oct";
                                break;
                            case 11: tempdate+="Nov";
                                break;
                            case 12: tempdate+="Dec";
                                break;
                        }
                        tempdate+=" "+day+", "+year;
                        date.setText(tempdate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        time_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Add_Reminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(year,month,day,hourOfDay,minute);
                        rem_time = String.valueOf(DateFormat.format("HH:mm",calendar1));
                        time.setText(DateFormat.format("HH:mm",calendar1));
                    }
                },Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE),true);
                timePickerDialog.show();
            }
        });

        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(rem_date==null||rem_time==null))
                {
                    remlList.add(remlList.size(),new Reminders_Card_Data(System.currentTimeMillis(),title.getText().toString().trim(),description.getText().toString().trim(),rem_date,rem_time,"0",rem_frequency.getText().toString().trim()));
                    saveData();
                    Toast.makeText(Add_Reminder.this, "Reminder saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Add_Reminder.this, Reminders.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(Add_Reminder.this, "Date and Time need to be set", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rem_frequency_chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] options = {"Never","Daily","Weekly","Monthly","Yearly","Custom"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Add_Reminder.this);
                builder.setTitle("Choose a frequency:")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which)
                        {
                            case 0: rem_frequency.setText("Never");
                                break;
                            case 1: rem_frequency.setText("Daily");
                                break;
                            case 2: rem_frequency.setText("Weekly");
                                break;
                            case 3: rem_frequency.setText("Monthly");
                                break;
                            case 4: rem_frequency.setText("Yearly");
                                break;
                            case 5: Intent intent = new Intent(Add_Reminder.this,Custom_Frequency_Setter.class);
                                    startActivity(intent);
                                break;
                        }
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

}