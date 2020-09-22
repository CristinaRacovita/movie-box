package com.example.homework.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.homework.services.ForegroundService;


public class ScreenReceiver extends BroadcastReceiver {
    public static final String SCREEN_STATE = "STATE";
    private boolean screenOff;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            screenOff = true;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            screenOff = false;
        }
        Intent i = new Intent(context, ForegroundService.class);
        i.putExtra(SCREEN_STATE, screenOff);
        context.startService(i);
    }
}
