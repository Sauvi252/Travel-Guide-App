package com.example.travelguideapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelguideapp.databinding.FragmentTripPlannerBinding;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TripPlannerFragment extends Fragment {

    private FragmentTripPlannerBinding binding;
    private GenerativeModelFutures model;
    
    // WARNING: Do not hardcode API keys in production. Use a backend
    private static final String GEMINI_API_KEY = "PASTE_YOUR_API_KEY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTripPlannerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Gemini Model
        GenerativeModel baseModel = new GenerativeModel("gemini-1.5-flash", GEMINI_API_KEY);
        model = GenerativeModelFutures.from(baseModel);

        binding.generateButton.setOnClickListener(v -> generateItinerary());
    }

    private void generateItinerary() {
        String destination = binding.destEditText.getText().toString().trim();
        String days = binding.daysEditText.getText().toString().trim();
        String budget = binding.budgetEditText.getText().toString().trim();

        if (destination.isEmpty() || days.isEmpty()) {
            Toast.makeText(getContext(), "Please enter destination and duration", Toast.LENGTH_SHORT).show();
            return;
        }

        if (GEMINI_API_KEY.isEmpty() || GEMINI_API_KEY.startsWith("PASTE_YOUR")) {
            Toast.makeText(getContext(), "Please add a valid Gemini API Key", Toast.LENGTH_LONG).show();
            return;
        }

        binding.aiProgressBar.setVisibility(View.VISIBLE);
        binding.generateButton.setEnabled(false);
        binding.itineraryCard.setVisibility(View.GONE);

        String prompt = String.format(
            "Create a detailed travel itinerary for a %s day trip to %s. " +
            "The total budget is around ₹%s. Include morning, afternoon, and evening activities for each day, " +
            "local food recommendations, and travel tips. Keep it structured and easy to read.",
            days, destination, budget.isEmpty() ? "any" : budget
        );

        Content content = new Content.Builder()
                .addText(prompt)
                .build();

        Executor executor = Executors.newSingleThreadExecutor();
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        binding.aiProgressBar.setVisibility(View.GONE);
                        binding.generateButton.setEnabled(true);
                        binding.itineraryCard.setVisibility(View.VISIBLE);
                        binding.itineraryText.setText(result.getText());
                    });
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        binding.aiProgressBar.setVisibility(View.GONE);
                        binding.generateButton.setEnabled(true);
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            }
        }, executor);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}