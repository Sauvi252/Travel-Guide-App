package com.example.travelguideapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelguideapp.databinding.FragmentStateGridBinding;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateGridFragment extends Fragment {

    private FragmentStateGridBinding binding;
    private final List<String> states = Arrays.asList(
            "Uttarakhand", "Rajasthan", "Goa", "Uttar Pradesh", "Punjab", "Delhi", 
            "Jammu and Kashmir", "Gujarat", "Maharashtra", "Karnataka", "Kerala", 
            "Tamil Nadu", "Telangana", "Andhra Pradesh", "West Bengal", "Bihar", 
            "Assam", "Sikkim", "Madhya Pradesh", "Odisha", "Himachal Pradesh"
    );

    // Sabhi states ke liye unique real image URLs
    private final Map<String, String> stateImages = new HashMap<String, String>() {{
        put("Uttarakhand", "https://images.unsplash.com/photo-1598977123418-45455513d1bd"); // Rishikesh
        put("Rajasthan", "https://images.unsplash.com/photo-1599661046289-e31887846eac"); // Hawa Mahal
        put("Goa", "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2"); // Beach
        put("Uttar Pradesh", "https://images.unsplash.com/photo-1564507592333-c60657eaa0af"); // Taj Mahal
        put("Punjab", "https://images.unsplash.com/photo-1514222134-b57cbb8ce073"); // Golden Temple
        put("Delhi", "https://images.unsplash.com/photo-1587474260584-1f20d430c39a"); // Qutub Minar
        put("Jammu and Kashmir", "https://images.unsplash.com/photo-1595152230681-34907949667f"); // Dal Lake
        put("Gujarat", "https://images.unsplash.com/photo-1627440614110-9aa6939920a0"); // Statue of Unity
        put("Maharashtra", "https://images.unsplash.com/photo-1529253355930-ddbe423a2ac7"); // Gateway of India
        put("Karnataka", "https://images.unsplash.com/photo-1600100397561-46808796853a"); // Hampi
        put("Kerala", "https://images.unsplash.com/photo-1602216056096-3b40cc0c9944"); // Houseboat
        put("Tamil Nadu", "https://images.unsplash.com/photo-1582510003544-4d00b7f74220"); // Meenakshi Temple
        put("Telangana", "https://images.unsplash.com/photo-1574515560126-7c3397940248"); // Charminar
        put("Andhra Pradesh", "https://images.unsplash.com/photo-1621213079633-87f544606f1b"); // Tirupati
        put("West Bengal", "https://images.unsplash.com/photo-1558431382-27e303142255"); // Victoria Memorial
        put("Bihar", "https://images.unsplash.com/photo-1561361513-2d000a50f0dc"); // Bodh Gaya
        put("Assam", "https://images.unsplash.com/photo-1571501679680-de32f139268e"); // Kaziranga
        put("Sikkim", "https://images.unsplash.com/photo-1589793907316-f9401541e8f0"); // Himalayas
        put("Madhya Pradesh", "https://images.unsplash.com/photo-1590050752117-23a9d7fc21c0"); // Khajuraho
        put("Odisha", "https://images.unsplash.com/photo-1605469597401-2c136366838a"); // Konark Sun Temple
        put("Himachal Pradesh", "https://images.unsplash.com/photo-1621360841013-c7683c659ec6"); // Shimla
    }};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStateGridBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.stateRecyclerView.setAdapter(new StateAdapter(states, stateImages, state -> {
            Bundle bundle = new Bundle();
            bundle.putString("selectedState", state);
            NavHostFragment.findNavController(this).navigate(R.id.action_StateGridFragment_to_FirstFragment, bundle);
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private static class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateViewHolder> {
        private final List<String> states;
        private final Map<String, String> stateImages;
        private final OnStateClickListener listener;

        interface OnStateClickListener {
            void onStateClick(String state);
        }

        public StateAdapter(List<String> states, Map<String, String> stateImages, OnStateClickListener listener) {
            this.states = states;
            this.stateImages = stateImages;
            this.listener = listener;
        }

        @NonNull
        @Override
        public StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_state, parent, false);
            return new StateViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull StateViewHolder holder, int position) {
            String state = states.get(position);
            holder.stateName.setText(state);
            
            String imageUrl = stateImages.get(state);
            if (imageUrl != null) {
                Glide.with(holder.itemView.getContext())
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_launcher_background)
                        .centerCrop()
                        .into(holder.stateImage);
            } else {
                holder.stateImage.setImageResource(R.drawable.ic_launcher_background);
            }
            
            holder.itemView.setOnClickListener(v -> listener.onStateClick(state));
        }

        @Override
        public int getItemCount() {
            return states.size();
        }

        static class StateViewHolder extends RecyclerView.ViewHolder {
            ImageView stateImage;
            TextView stateName;

            public StateViewHolder(@NonNull View itemView) {
                super(itemView);
                stateImage = itemView.findViewById(R.id.stateImage);
                stateName = itemView.findViewById(R.id.stateName);
            }
        }
    }
}