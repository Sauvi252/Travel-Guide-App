package com.example.travelguideapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.travelguideapp.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.logoutButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_FirstFragment_to_LoginFragment);
        });

        binding.myTripsButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_ProfileFragment_to_TripPlannerFragment);
        });

        binding.digitalPassportButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_ProfileFragment_to_DigitalPassportFragment);
        });

        binding.expenseTrackerButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_ProfileFragment_to_ExpenseTrackerFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}