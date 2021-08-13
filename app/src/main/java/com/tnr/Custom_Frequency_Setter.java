package com.tnr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class Custom_Frequency_Setter extends AppCompatActivity {

    private ImageButton sunday;
    private ImageButton monday;
    private ImageButton tuesday;
    private ImageButton wednesday;
    private ImageButton thursday;
    private ImageButton friday;
    private ImageButton saturday;
    private ExtendedFloatingActionButton custom_done;
    private ExtendedFloatingActionButton custom_discard;
    private TextView end_date;
    int a=0,b=0,c=0,d=0,e=0,f=0,g=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_frequency_setter);
        getSupportActionBar().setTitle("Set custom recurrence");
        getSupportActionBar().setHomeButtonEnabled(false);

        sunday = findViewById(R.id.Sunday_button);
        monday = findViewById(R.id.Monday_button);
        tuesday = findViewById(R.id.Tuesday_button);
        wednesday = findViewById(R.id.Wednesday_button);
        thursday = findViewById(R.id.Thursday_button);
        friday = findViewById(R.id.Friday_button);
        saturday = findViewById(R.id.Saturday_button);
        custom_done = findViewById(R.id.custom_done);
        custom_discard = findViewById(R.id.custom_discard);
        end_date = findViewById(R.id.end_date);

        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a%2==0)
                {
                    sunday.setBackground(getDrawable(R.drawable.custom_button_shape_selected));
                }
                else
                {
                    sunday.setBackground(getDrawable(R.drawable.custom_button_shape));
                }
                a++;
            }
        });
        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b%2==0)
                {
                    monday.setBackground(getDrawable(R.drawable.custom_button_shape_selected));
                }
                else
                {
                    monday.setBackground(getDrawable(R.drawable.custom_button_shape));
                }
                b++;
            }
        });
        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c%2==0)
                {
                    tuesday.setBackground(getDrawable(R.drawable.custom_button_shape_selected));
                }
                else
                {
                    tuesday.setBackground(getDrawable(R.drawable.custom_button_shape));
                }
                c++;
            }
        });
        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(d%2==0)
                {
                    wednesday.setBackground(getDrawable(R.drawable.custom_button_shape_selected));
                }
                else
                {
                    wednesday.setBackground(getDrawable(R.drawable.custom_button_shape));
                }
                d++;
            }
        });
        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e%2==0)
                {
                    thursday.setBackground(getDrawable(R.drawable.custom_button_shape_selected));
                }
                else
                {
                    thursday.setBackground(getDrawable(R.drawable.custom_button_shape));
                }
                e++;
            }
        });
        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f%2==0)
                {
                    friday.setBackground(getDrawable(R.drawable.custom_button_shape_selected));
                }
                else
                {
                    friday.setBackground(getDrawable(R.drawable.custom_button_shape));
                }
                f++;
            }
        });
        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(g%2==0)
                {
                    saturday.setBackground(getDrawable(R.drawable.custom_button_shape_selected));
                }
                else
                {
                    saturday.setBackground(getDrawable(R.drawable.custom_button_shape));
                }
                g++;
            }
        });

        custom_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Custom_Frequency_Setter.this);
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
                                Intent intent = new Intent(Custom_Frequency_Setter.this, Add_Reminder.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                ;
                builder.create().show();
            }
        });

    }
}