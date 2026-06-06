package com.example.travelguideapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private List<TouristPlace> placeList;
    private List<TouristPlace> placeListFull;
    private OnPlaceClickListener listener;
    private String currentState = "All States";
    private String currentSearch = "";

    public interface OnPlaceClickListener {
        void onPlaceClick(TouristPlace place);
    }

    public PlaceAdapter(List<TouristPlace> placeList, OnPlaceClickListener listener) {
        this.placeList = placeList;
        this.placeListFull = new ArrayList<>(placeList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tourist_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        TouristPlace place = placeList.get(position);
        holder.nameTextView.setText(place.getName());
        holder.locationTextView.setText(place.getLocation());
        
        Glide.with(holder.itemView.getContext())
                .load(place.getImageUrl())
                .override(300, 300)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> listener.onPlaceClick(place));
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public void filter(String text) {
        currentSearch = text.toLowerCase();
        applyFilters();
    }

    public void filterByState(String state) {
        currentState = state;
        applyFilters();
    }

    private void applyFilters() {
        placeList = new ArrayList<>();
        for (TouristPlace item : placeListFull) {
            boolean stateMatch = currentState.equals("All States") || item.getState().equals(currentState);
            boolean searchMatch = currentSearch.isEmpty() || 
                                  item.getName().toLowerCase().contains(currentSearch) || 
                                  item.getLocation().toLowerCase().contains(currentSearch) ||
                                  item.getState().toLowerCase().contains(currentSearch);
            
            if (stateMatch && searchMatch) {
                placeList.add(item);
            }
        }
        notifyDataSetChanged();
    }

    static class PlaceViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView locationTextView;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.placeImage);
            nameTextView = itemView.findViewById(R.id.placeName);
            locationTextView = itemView.findViewById(R.id.placeLocation);
        }
    }
}
