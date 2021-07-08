package com.tnr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 1000;
    private Animation appear;
    private ImageView logo;
    private TextView wmrka;
    private TextView wmrkb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.logo_splash);
        wmrka = findViewById(R.id.wmrka);
        wmrkb = findViewById(R.id.wmrkb);

        appear = AnimationUtils.loadAnimation(this,R.anim.appear_animation);

        logo.setAnimation(appear);
        wmrka.setAnimation(appear);
        wmrkb.setAnimation(appear);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Dashboard.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIMEOUT);

    }
}