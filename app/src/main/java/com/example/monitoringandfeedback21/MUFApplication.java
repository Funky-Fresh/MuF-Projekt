package com.example.monitoringandfeedback21;

import android.app.Application;

import androidx.room.Room;

public class MUFApplication extends Application {
    private MUFDatabase mufDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mufDatabase = Room
                .databaseBuilder(this, MUFDatabase.class, "acceldata")
                .build();
    }

    public MUFDatabase getDatabase() {return mufDatabase;}

}
