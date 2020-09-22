package com.example.homework.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homework.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView splashImage = findViewById(R.id.splash_image);

        int SPLASH_TIME_OUT = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences mSharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.PREFERENCES_TAG, Context.MODE_PRIVATE);
                boolean rememberBoolean = mSharedPreferences.getBoolean(SingInActivity.REMEMBER_TAG, false);
                String username = mSharedPreferences.getString(SingInActivity.USERNAME_TAG, null);
                if (rememberBoolean) {
                    Intent homeIntent = new Intent(SplashActivity.this, MainActivity.class);
                    homeIntent.putExtra(SingInActivity.USERNAME_TAG, username);
                    startActivity(homeIntent);
                    finish();
                } else {
                    Intent homeIntent = new Intent(SplashActivity.this, SingInActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

        splashImage.setAlpha(0.2f);
        splashImage.animate()
                .alpha(1f)
                .setDuration(700);

    }
}
