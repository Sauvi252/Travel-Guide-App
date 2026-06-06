package com.example.travelguideapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelguideapp.databinding.FragmentArGuideBinding;
import com.google.ar.core.ArCoreApk;

public class ARGuideFragment extends Fragment {

    private FragmentArGuideBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentArGuideBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkARAvailability();
        
        // Mocking a discovery
        view.postDelayed(() -> {
            binding.arStatusText.setText("Landmark Detected!");
            binding.arInfoCard.setVisibility(View.VISIBLE);
            binding.arTitle.setText("India Gate");
            binding.arDescription.setText("The India Gate is a war memorial located astride the Rajpath, on the eastern edge of the 'ceremonial axis' of New Delhi.");
        }, 3000);
    }

    private void checkARAvailability() {
        if (getContext() == null) return;
        ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(getContext());
        if (availability.isTransient()) {
            // Continue checking
            if (getView() != null) {
                getView().postDelayed(this::checkARAvailability, 200);
            }
        }
        if (!availability.isSupported()) {
            Toast.makeText(getContext(), "AR is not supported on this device", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}