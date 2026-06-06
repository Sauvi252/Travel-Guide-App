package com.example.travelguideapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.travelguideapp.databinding.FragmentFirstBinding;
import com.example.travelguideapp.databinding.ItemAiRecomBinding;
import com.google.android.material.chip.Chip;

import android.text.Editable;
import android.text.TextWatcher;
import android.speech.RecognizerIntent;
import android.content.Intent;
import android.app.Activity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private PlaceAdapter adapter;

    private final ActivityResultLauncher<Intent> voiceSearchLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    ArrayList<String> matches = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (matches != null && !matches.isEmpty()) {
                        binding.searchEditText.setText(matches.get(0));
                    }
                }
            }
    );

    private void startVoiceSearch() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Where do you want to go?");
        try {
            voiceSearchLauncher.launch(intent);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Voice search not supported", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void setupAIRecommendations() {
        // Updated with real Unsplash images
        String[][] aiPicks = {
            {"Valley of Flowers", "Uttarakhand", "https://images.unsplash.com/photo-1627440614110-9aa6939920a0"},
            {"Rann of Kutch", "Gujarat", "https://images.unsplash.com/photo-1627440614110-9aa6939920a0"},
            {"Munnar Tea Gardens", "Kerala", "https://images.unsplash.com/photo-1593181629936-11c609b8db9b"}
        };

        for (String[] pick : aiPicks) {
            ItemAiRecomBinding itemBinding = ItemAiRecomBinding.inflate(getLayoutInflater(), binding.aiRecomLayout, false);
            itemBinding.recomName.setText(pick[0]);
            // Glide for images
            com.bumptech.glide.Glide.with(this)
                    .load(pick[2])
                    .centerCrop()
                    .into(itemBinding.recomImage);
            
            itemBinding.getRoot().setOnClickListener(v -> {
                Toast.makeText(getContext(), "Launching 360° VR Preview for " + pick[0], Toast.LENGTH_SHORT).show();
            });
            
            binding.aiRecomLayout.addView(itemBinding.getRoot());
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.progressBar.setVisibility(View.VISIBLE);
        setupAIRecommendations();

        binding.fabAR.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.ARGuideFragment);
        });

        binding.btnVoiceSearch.setOnClickListener(v -> startVoiceSearch());

        // Handle selected state from arguments
        String initialFilterValue = "All States";
        if (getArguments() != null && getArguments().containsKey("selectedState")) {
            initialFilterValue = getArguments().getString("selectedState");
        }

        final String finalInitialFilter = initialFilterValue;

        // Fetch data in background to prevent UI freeze
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            List<TouristPlace> places = dbHelper.getAllPlaces();

            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    adapter = new PlaceAdapter(places, place -> {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("place", place);
                        NavHostFragment.findNavController(FirstFragment.this)
                                .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
                    });

                    binding.recyclerViewPlaces.setAdapter(adapter);

                    // Apply initial filter if any
                    if (!finalInitialFilter.equals("All States")) {
                        adapter.filterByState(finalInitialFilter);
                        // Also check the corresponding chip
                        for (int i = 0; i < binding.stateChipGroup.getChildCount(); i++) {
                            Chip chip = (Chip) binding.stateChipGroup.getChildAt(i);
                            if (chip.getText().toString().equalsIgnoreCase(finalInitialFilter)) {
                                chip.setChecked(true);
                                // Scroll to the chip
                                binding.stateScroll.smoothScrollTo(chip.getLeft(), 0);
                                break;
                            }
                        }
                    }

                    // Search Filter
                    binding.searchEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (adapter != null) {
                                adapter.filter(s.toString());
                                updateNoResultsVisibility();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {}
                    });

                    // State Filter (Chips)
                    binding.stateChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
                        if (!checkedIds.isEmpty()) {
                            Chip chip = group.findViewById(checkedIds.get(0));
                            if (chip != null && adapter != null) {
                                adapter.filterByState(chip.getText().toString());
                                updateNoResultsVisibility();
                            }
                        }
                    });
                });
            }
        });
    }

    private void updateNoResultsVisibility() {
        if (adapter != null) {
            if (adapter.getItemCount() == 0) {
                binding.noResultsText.setVisibility(View.VISIBLE);
            } else {
                binding.noResultsText.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}