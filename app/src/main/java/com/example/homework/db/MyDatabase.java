package com.example.homework.db;

import android.content.Context;

import androidx.room.Room;

public class MyDatabase {
    private Context mCtx;
    private static MyDatabase mInstance;
    private AppDatabase appDatabase;

    private MyDatabase(Context mCtx) {
        this.mCtx = mCtx;
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "MyToDos").allowMainThreadQueries().build();
    }

    public static synchronized MyDatabase getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new MyDatabase(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
