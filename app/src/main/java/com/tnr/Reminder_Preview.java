package com.tnr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Reminder_Preview extends AppCompatActivity {

    private List<Reminder_card_data> remlList;
    private int position;
    private long card_id;
    private EditText prev_title;
    private TextView prev_date;
    private TextView prev_time;
    private EditText time_offset;
    private EditText prev_desc;
    private String rem_date;
    private String rem_time;

    private String extract_day ()
    {
        String daystr = "";
        for(int i=0;i<rem_date.length();i++)
        {
            if(rem_date.charAt(i)=='/')
                break;
            else
                daystr+=rem_date.charAt(i);
        }
        return daystr;
    }

    private String extract_month ()
    {
        int i;
        String monthstr = "";
        for(i=0;i<rem_date.length();i++)
        {
            if(rem_date.charAt(i)=='/')
                break;
        }
        for(i=i+1;i<rem_date.length();i++)
        {
            if(rem_date.charAt(i)=='/')
                break;
            else
                monthstr+=rem_date.charAt(i);
        }
        return monthstr;
    }

    private String extract_year ()
    {
        int i;
        String yearstr = "";
        for(i=rem_date.length()-1;i>=0;i--)
        {
            if(rem_date.charAt(i)=='/')
                break;
        }
        for(i=i+1;i<rem_date.length();i++)
        {
            yearstr+=rem_date.charAt(i);
        }
        return yearstr;
    }

    private int extract_hour()
    {
        String hr = "";
        for(int i=0;i<rem_time.length();i++)
        {
            if(rem_time.charAt(i)==':')
                break;
            else
                hr+=rem_time.charAt(i);
        }
        return Integer.parseInt(hr);
    }

    private int extract_minute()
    {
        String min = "";
        int i;
        for(i=rem_time.length()-1;i>=0;i--)
        {
            if(rem_time.charAt(i)==':')
                break;
        }
        for(i=i+1;i<rem_time.length();i++)
        {
            min+=rem_time.charAt(i);
        }
        return  Integer.parseInt(min);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_preview);
        getSupportActionBar().setHomeButtonEnabled(true);

        loadData();

        card_id = getIntent().getLongExtra("card_id",0);
        for(int i=0;i<remlList.size();i++)
        {
            if(remlList.get(i).getId()==card_id)
            {
                position=i;
                break;
            }
        }
        rem_date = remlList.get(position).getDate();
        rem_time = remlList.get(position).getTime();

        getSupportActionBar().setTitle(remlList.get(position).getTitle());

        final int year = Integer.parseInt(extract_year());
        final int month = Integer.parseInt(extract_month())-1;
        final int day = Integer.parseInt(extract_day());

        prev_title = findViewById(R.id.prev_title);
        prev_date = findViewById(R.id.prev_date);
        prev_time = findViewById(R.id.prev_time);
        time_offset = findViewById(R.id.time_offset_for_rem);
        prev_desc = findViewById(R.id.prev_desc);

        prev_title.setText(remlList.get(position).getTitle());
        String tempdate=day+" ";
        switch (month)
        {
            case 0: tempdate+="Jan";
                break;
            case 1: tempdate+="Feb";
                break;
            case 2: tempdate+="Mar";
                break;
            case 3: tempdate+="Apr";
                break;
            case 4: tempdate+="May";
                break;
            case 5: tempdate+="Jun";
                break;
            case 6: tempdate+="Jul";
                break;
            case 7: tempdate+="Aug";
                break;
            case 8: tempdate+="Sept";
                break;
            case 9: tempdate+="Oct";
                break;
            case 10: tempdate+="Nov";
                break;
            case 11: tempdate+="Dec";
                break;
        }
        tempdate+=", "+year;
        prev_date.setText(tempdate);
        prev_time.setText(remlList.get(position).getTime());
        time_offset.setText(remlList.get(position).getTime_offset());
        prev_desc.setText(remlList.get(position).getDescription());

        prev_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Reminder_Preview.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(year,month,day,hourOfDay,minute);
                        prev_time.setText(DateFormat.format("HH:mm",calendar1));
                    }
                },extract_hour(),extract_minute(),true);
                timePickerDialog.show();
            }
        });

        prev_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Reminder_Preview.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        rem_date = day+"/"+month+"/"+year;
                        String tempdate=day+" ";
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
                        tempdate+=", "+year;
                        prev_date.setText(tempdate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.reminder_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.save_edited_rem: save_rem_edits();
                break;
            case android.R.id.home: startActivity(new Intent(Reminder_Preview.this, Reminder.class));
                break;
        }
        return true;
    }

    private void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("reminder_activity_sp",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("doc_list",null);
        Type type = new TypeToken<ArrayList<Reminder_card_data>>(){}.getType();
        remlList = gson.fromJson(json,type);
        if(remlList==null)
        {
            remlList = new ArrayList<Reminder_card_data>();
        }
    }

    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("reminder_activity_sp",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(remlList);
        editor.putString("doc_list",json);
        editor.apply();
    }

    private void save_rem_edits()
    {
        remlList.get(position).setTitle(prev_title.getText().toString().trim());
        remlList.get(position).setDescription(prev_desc.getText().toString().trim());
        remlList.get(position).setTime(prev_time.getText().toString().trim());
        remlList.get(position).setDate(rem_date);
        remlList.get(position).setTime_offset(time_offset.getText().toString().trim());
        saveData();
        Toast.makeText(this, "Edits saved", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Reminder_Preview.this,Reminder.class);
        startActivity(intent);
        finish();
    }

}