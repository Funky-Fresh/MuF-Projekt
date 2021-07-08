package com.example.monitoringandfeedback21;

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
        TextView dataDescription = view.findViewById(R.id.textView);
        TextView sensorData = view.findViewById(R.id.sensorData);

        //mainViewModel.getDatabase().getAccelerationDao().getAllData().observe(getViewLifecycleOwner(), (accelerationInformation) -> {
        //  sensorData.setText("ID: " + accelerationInformation.getId() + " X: " + accelerationInformation.getX() + " Y: " + accelerationInformation.getY() + " Z " + accelerationInformation.getZ());
        //});
        accelerationLiveData = (MainViewModel.AccelerationLiveData) mainViewModel.accelerationInsert();
        accelerationLiveData.getSensorData().observe(getViewLifecycleOwner(), (accelerationInformation) -> {
            sensorData.setText("ID: " + accelerationInformation.getId() + " X: " + accelerationInformation.getX() + " Y: " + accelerationInformation.getY() + " Z " + accelerationInformation.getZ());
        });

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
