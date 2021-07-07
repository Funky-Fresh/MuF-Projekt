package com.example.monitoringandfeedback21;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.monitoringandfeedback21.dao.AccelerationDao;

@Database(entities = {AccelerationInformation.class}, version = 1, exportSchema = false)
public abstract class MUFDatabase extends RoomDatabase {
    public abstract AccelerationDao getAccelerationDao();
}
