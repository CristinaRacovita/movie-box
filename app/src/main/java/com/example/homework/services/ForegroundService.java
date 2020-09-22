package com.example.homework.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.homework.R;
import com.example.homework.activities.SingInActivity;
import com.example.homework.receivers.ScreenReceiver;

import static com.example.homework.activities.SingInActivity.SERVICE_INPUT;
import static com.example.homework.receivers.ScreenReceiver.SCREEN_STATE;

public class ForegroundService extends Service {

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    public static final String SERVICE_NAME = "Foreground Service Channel";
    public static final String SERVICE_TITLE1 = "Upppsss, you're logged in as a guest";
    public static final String SERVICE_TITLE2 = "Heii, me again!:) you're logged in as a guest";
    private static final long DEFAULT_TIMEOUT = (long) (0.5 * 60 * 1000);
    private BroadcastReceiver mReceiver = new ScreenReceiver();

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        final boolean screenOff = intent.getBooleanExtra(SCREEN_STATE, false);

        if (!screenOff) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    createMessage(intent, SERVICE_TITLE1);
                }
            }, DEFAULT_TIMEOUT);
        }

        if (screenOff) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    createMessage(intent, SERVICE_TITLE2);
                }
            }, DEFAULT_TIMEOUT);
        }

        return START_NOT_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createMessage(Intent intent, String title) {
        String input = intent.getStringExtra(SERVICE_INPUT);

        createNotificationChannel();

        Intent notificationIntent = new Intent(this, SingInActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_error_24dp)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel serviceChannel = new NotificationChannel(
                CHANNEL_ID,
                SERVICE_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(serviceChannel);
    }
}

