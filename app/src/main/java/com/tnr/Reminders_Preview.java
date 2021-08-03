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

public class Reminders_Preview extends AppCompatActivity {

    private ExtendedFloatingActionButton edit;
    private List<Reminders_Card_Data> remlList;
    private long card_id;
    private int position;
    private TextView prev_title;
    private TextView prev_date;
    private TextView prev_time;
    private TextView time_offset;
    private TextView prev_desc;
    private TextView rem_freq;
    private String rem_date;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders_preview);
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

        getSupportActionBar().setTitle(remlList.get(position).getTitle());

        final int year = Integer.parseInt(extract_year());
        final int month = Integer.parseInt(extract_month())-1;
        final int day = Integer.parseInt(extract_day());

        edit = findViewById(R.id.make_rem_changes);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reminders_Preview.this, Reminders_Edit.class);
                intent.putExtra("card_id",remlList.get(position).getId());
                startActivity(intent);
            }
        });
        prev_title = findViewById(R.id.prev_title);
        prev_date = findViewById(R.id.prev_date);
        prev_time = findViewById(R.id.prev_time);
        time_offset = findViewById(R.id.time_offset_for_rem_prev);
        prev_desc = findViewById(R.id.prev_desc);
        rem_freq = findViewById(R.id.reminder_frequency_prev);

        String tempdate="";
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
        tempdate+=" "+day+", "+year;
        prev_date.setText(tempdate);
        prev_time.setText(remlList.get(position).getTime());
        prev_title.setText(remlList.get(position).getTitle());
        prev_desc.setText(remlList.get(position).getDescription());
        time_offset.setText(remlList.get(position).getTime_offset());
        rem_freq.setText(remlList.get(position).getFrequency());

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

}