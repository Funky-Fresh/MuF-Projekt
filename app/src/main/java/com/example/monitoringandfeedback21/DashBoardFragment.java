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

public class DashBoardFragment extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_dashboard,container,false);
        TextView dashboardTitleView = view.findViewById(R.id.text1);
        Bundle args = getArguments();
        DashBoardFragmentArgs dashBoardFragmentArgs = null;
        if (args != null) {
            dashBoardFragmentArgs = DashBoardFragmentArgs.fromBundle(args);
        }

        if (dashBoardFragmentArgs != null) {
            dashboardTitleView.setText(dashBoardFragmentArgs.getDashboardTitle());
        }

        final TextView vendor = view.findViewById(R.id.vendor);
        final TextView name = view.findViewById(R.id.name);
        final TextView version = view.findViewById(R.id.version);
        final TextView resolution = view.findViewById(R.id.resolution);
        final TextView maxrange = view.findViewById(R.id.maxrange);
        final TextView power = view.findViewById(R.id.vendor);
        final TextView xyz = view.findViewById(R.id.xyz);

        view.findViewById(R.id.startAccel).setOnClickListener(v -> {

            accelerationLiveData = (MainViewModel.AccelerationLiveData) mainViewModel.accelerationInsert();

            accelerationLiveData.onActive();
            accelerationLiveData.observe(getViewLifecycleOwner(), (accelerationInformation) -> {
                vendor.setText("Vendor " + accelerationInformation.getSensor().getVendor());
                name.setText("Name " + accelerationInformation.getSensor().getName());
                version.setText("Version " + accelerationInformation.getSensor().getVersion());
                resolution.setText("Resolution " + accelerationInformation.getSensor().getResolution());
                maxrange.setText("MaxRange " + accelerationInformation.getSensor().getMaximumRange());
                power.setText("Power " + accelerationInformation.getSensor().getPower());
                xyz.setText("X: " + accelerationInformation.getX() + " Y: " + accelerationInformation.getY() + " Z: " + accelerationInformation.getZ());

            });
        });

        view.findViewById(R.id.stopAccel).setOnClickListener(v -> {
            accelerationLiveData.onInactive();
        });

        view.findViewById(R.id.deleteData).setOnClickListener(v -> {
            accelerationLiveData.deleteSensorData();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final NavController controller = Navigation.findNavController(view);
        view.findViewById(R.id.buttonstop).setOnClickListener(button -> {
            controller.navigate(DashBoardFragmentDirections.actionDashBoardToStartFragment());
        });
        view.findViewById(R.id.buttonmonitor).setOnClickListener(buttonmonitor -> {
            controller.navigate(DashBoardFragmentDirections.actionDashBoardFragmentToMonitorFragment());
        });
    }
}
