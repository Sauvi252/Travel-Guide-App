package com.example.travelguideapp;

import java.io.Serializable;

public class TouristPlace implements Serializable {
    private String name;
    private String state;
    private String description;
    private String history;
    private String bestTime;
    private String timing;
    private String entryFee;
    private String imageUrl;
    private String location;
    private String nearbyPlaces;
    private String travelTips;

    public TouristPlace(String name, String state, String description, String history, String bestTime, 
                        String timing, String entryFee, String imageUrl, String location, 
                        String nearbyPlaces, String travelTips) {
        this.name = name;
        this.state = state;
        this.description = description;
        this.history = history;
        this.bestTime = bestTime;
        this.timing = timing;
        this.entryFee = entryFee;
        this.imageUrl = imageUrl;
        this.location = location;
        this.nearbyPlaces = nearbyPlaces;
        this.travelTips = travelTips;
    }

    public String getName() { return name; }
    public String getState() { return state; }
    public String getDescription() { return description; }
    public String getHistory() { return history; }
    public String getBestTime() { return bestTime; }
    public String getTiming() { return timing; }
    public String getEntryFee() { return entryFee; }
    public String getImageUrl() { return imageUrl; }
    public String getLocation() { return location; }
    public String getNearbyPlaces() { return nearbyPlaces; }
    public String getTravelTips() { return travelTips; }
}
