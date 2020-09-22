package com.example.homework.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homework.R;
import com.example.homework.services.ForegroundService;

public class WelcomeSplashActivity extends AppCompatActivity {

    public final String SERVICE_NOT_WORKING = "Logged in successfully!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_splash);

        TextView textUsername = findViewById(R.id.usernameText);

        final String username_value = getIntent().getStringExtra(SingInActivity.USERNAME_TAG);
        if (username_value != null) {
            textUsername.setText(username_value);
        }

        int SPLASH_TIME_OUT = 1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(WelcomeSplashActivity.this, MainActivity.class);
                homeIntent.putExtra(SingInActivity.USERNAME_TAG, username_value);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);

        if (!isMyServiceRunning(ForegroundService.class)){
            Toast.makeText(this,SERVICE_NOT_WORKING,Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}