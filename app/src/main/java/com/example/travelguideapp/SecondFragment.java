package com.example.travelguideapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.travelguideapp.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            TouristPlace place = (TouristPlace) getArguments().getSerializable("place");
            if (place != null) {
                binding.detailName.setText(place.getName());
                binding.detailState.setText(place.getState());
                binding.detailLocation.setText(place.getLocation());
                binding.detailHistory.setText(place.getHistory());
                binding.detailBestTime.setText(place.getBestTime());
                binding.detailTiming.setText(place.getTiming());
                binding.detailFee.setText(place.getEntryFee());
                binding.detailNearby.setText(place.getNearbyPlaces());
                binding.detailTips.setText(place.getTravelTips());
                
                Glide.with(this)
                        .load(place.getImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .error(android.R.drawable.ic_menu_report_image)
                        .into(binding.detailImage);

                binding.buttonMap.setOnClickListener(v -> {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(place.getName() + ", " + place.getLocation()));
                    openMapIntent(gmmIntentUri);
                });

                binding.buttonHotels.setOnClickListener(v -> {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=hotels near " + Uri.encode(place.getName() + ", " + place.getLocation()));
                    openMapIntent(gmmIntentUri);
                });

                binding.buttonRestaurants.setOnClickListener(v -> {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=restaurants near " + Uri.encode(place.getName() + ", " + place.getLocation()));
                    openMapIntent(gmmIntentUri);
                });

                binding.buttonBuddy.setOnClickListener(v -> {
                    NavHostFragment.findNavController(this).navigate(R.id.action_SecondFragment_to_BuddyFragment);
                });
            }
        }

        binding.buttonSecond.setOnClickListener(v ->
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment)
        );
    }

    private void openMapIntent(Uri gmmIntentUri) {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (getActivity() != null && mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            startActivity(new Intent(Intent.ACTION_VIEW, gmmIntentUri));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
