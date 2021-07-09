package com.example.monitoringandfeedback21.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.monitoringandfeedback21.MainViewModel;
import com.example.monitoringandfeedback21.fragments.MonitorFragmentDirections;
import com.example.monitoringandfeedback21.R;

public class MonitorFragment extends Fragment {
    private MainViewModel mainViewModel;
    private MainViewModel.AccelerationLiveData accelerationLiveData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitor,container,false);
        //TextView dataDescription = view.findViewById(R.id.textView);
        TextView sensorData = view.findViewById(R.id.sensorData);


        accelerationLiveData = (MainViewModel.AccelerationLiveData) mainViewModel.getAccelerationLiveData();

        accelerationLiveData.getSensorData().observe(getViewLifecycleOwner(), (accelerationInformation) -> {
            if (accelerationInformation != null) {
                sensorData.setText("ID: " + accelerationInformation.getId() + " X: " + accelerationInformation.getX() + " Y: " + accelerationInformation.getY() + " Z " + accelerationInformation.getZ());
            } else {
                sensorData.setText("No data available.");
            }
        });

        // List - uncomment to use AccelerationInformation list.
        /*
        accelerationLiveData.getSensorDataList().observe(getViewLifecycleOwner(), (accelerationInformation) -> {
            int idx = 20;
            if ((!accelerationInformation.isEmpty()) & (accelerationInformation.size() >= idx)) {
                sensorData.setText("ID: " + accelerationInformation.get(idx).getId() + " X: " + accelerationInformation.get(idx).getX() + " Y: " + accelerationInformation.get(idx).getY() + " Z " + accelerationInformation.get(idx).getZ());
            } else {
                sensorData.setText("No data available.");
            }
        });
         */


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final NavController controller2 = Navigation.findNavController(view);
        view.findViewById(R.id.backToDashboard).setOnClickListener(button -> {
            controller2.navigate(MonitorFragmentDirections.actionMonitorFragmentToDashBoardFragment("Informations: "));
        });
    }
}
