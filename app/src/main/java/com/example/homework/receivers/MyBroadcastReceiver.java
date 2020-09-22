package com.example.homework.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.homework.activities.WelcomeSplashActivity;
import com.example.homework.services.ForegroundService;

import static com.example.homework.activities.SingInActivity.SERVICE_INPUT;
import static com.example.homework.activities.SingInActivity.SERVICE_TEXT;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public final String BOOT_MESSAGE = "PHONE BOOTED";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent myIntent = new Intent(context, WelcomeSplashActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);

        Toast.makeText(context, BOOT_MESSAGE, Toast.LENGTH_LONG).show();

        Intent serviceIntent = new Intent(context, ForegroundService.class);
        serviceIntent.putExtra(SERVICE_INPUT, SERVICE_TEXT);
        context.startService(serviceIntent);
    }
}
