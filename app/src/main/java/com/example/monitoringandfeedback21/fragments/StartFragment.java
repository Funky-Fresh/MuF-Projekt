package com.example.monitoringandfeedback21.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.monitoringandfeedback21.R;
import com.example.monitoringandfeedback21.fragments.StartFragmentDirections;

public class StartFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_startfragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final NavController controller = Navigation.findNavController(view);
        view.findViewById(R.id.button).setOnClickListener(button ->  {
            controller
                    .navigate(
                            StartFragmentDirections
                                    .actionStartFragmentToDashBoardFragment("Informations: ")
                    );
        });
    }
}
