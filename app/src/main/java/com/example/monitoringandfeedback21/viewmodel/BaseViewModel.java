package com.example.monitoringandfeedback21.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.monitoringandfeedback21.MUFApplication;
import com.example.monitoringandfeedback21.MUFDatabase;

public abstract class BaseViewModel extends AndroidViewModel {
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public MUFDatabase getDatabase() {
        return ((MUFApplication)getApplication()).getDatabase();
    }
}
