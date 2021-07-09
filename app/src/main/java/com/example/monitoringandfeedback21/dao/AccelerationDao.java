package com.example.monitoringandfeedback21.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.monitoringandfeedback21.AccelerationInformation;

import java.util.List;


@Dao
public abstract class AccelerationDao {

    @Query("SELECT * FROM accelxyz")
    public abstract LiveData<AccelerationInformation> getAllData();

    // List - to be used, see and uncomment in MonitorFragment
    @Query("SELECT * FROM accelxyz")
    public abstract LiveData<List<AccelerationInformation>> getAllDataList();

    @Insert
    public abstract long insert(AccelerationInformation accelerationInformation);

    @Query("DELETE FROM accelxyz")
    public abstract void deleteAllData();


}
