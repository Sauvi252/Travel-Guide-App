package com.example.travelguideapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartTravelGuide.db";
    private static final int DATABASE_VERSION = 15; // Final detailed update

    public static final String TABLE_PLACES = "tourist_places";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_HISTORY = "history";
    public static final String COLUMN_BEST_TIME = "best_time";
    public static final String COLUMN_TIMING = "timing";
    public static final String COLUMN_ENTRY_FEE = "entry_fee";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_NEARBY = "nearby_places";
    public static final String COLUMN_TIPS = "travel_tips";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PLACES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_STATE + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_HISTORY + " TEXT, " +
                    COLUMN_BEST_TIME + " TEXT, " +
                    COLUMN_TIMING + " TEXT, " +
                    COLUMN_ENTRY_FEE + " TEXT, " +
                    COLUMN_IMAGE_URL + " TEXT, " +
                    COLUMN_LOCATION + " TEXT, " +
                    COLUMN_NEARBY + " TEXT, " +
                    COLUMN_TIPS + " TEXT" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        insertDefaultData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        onCreate(db);
    }

    private void insertDefaultData(SQLiteDatabase db) {
        // --- UTTARAKHAND ---
        addPlace(db, "Rishikesh", "Uttarakhand", "Yoga Capital of the World.", "Historical spiritual center.", "Mar-May", "24 Hours", "Free", "https://images.unsplash.com/photo-1598977123418-45455513d1bd", "Rishikesh", "Haridwar", "Try White Water Rafting.");
        addPlace(db, "Haridwar", "Uttarakhand", "Ganga Aarti destination.", "Ancient holy city.", "Oct-Mar", "24 Hours", "Free", "https://images.unsplash.com/photo-1564507592333-c60657eaa0af", "Haridwar", "Rishikesh", "Watch Aarti at evening.");
        addPlace(db, "Mussoorie", "Uttarakhand", "Queen of Hills.", "British era hill station.", "Apr-Jun", "24 Hours", "Free", "https://images.unsplash.com/photo-1622308644420-b30176840781", "Dehradun", "Kempty Falls", "Mall Road walk.");
        addPlace(db, "Kedarnath", "Uttarakhand", "Lord Shiva Temple.", "One of Char Dhams.", "May-Oct", "6 AM-9 PM", "Free", "https://images.unsplash.com/photo-1624386403664-90231940a430", "Rudraprayag", "Gaurikund", "16km Trek.");
        addPlace(db, "Auli", "Uttarakhand", "Skiing Paradise.", "Snowy Himalayan views.", "Dec-Mar", "24 Hours", "Free", "https://images.unsplash.com/photo-1616423642771-7006886e811c", "Chamoli", "Joshimath", "Take Ropeway.");

        // --- HIMACHAL PRADESH ---
        addPlace(db, "Shimla", "Himachal Pradesh", "Summer Capital of British India.", "Mall Road and Ridge.", "Mar-Jun", "24 Hours", "Free", "https://images.unsplash.com/photo-1621360841013-c7683c659ec6", "Shimla", "Kufri", "Take the Toy Train.");
        addPlace(db, "Manali", "Himachal Pradesh", "High-altitude Himalayan resort.", "Gateway for adventure.", "Oct-Jun", "24 Hours", "Free", "https://images.unsplash.com/photo-1593181629936-11c609b8db9b", "Kullu", "Rohtang Pass", "Visit Hadimba Temple.");

        // --- BIHAR ---
        addPlace(db, "Mahabodhi Temple", "Bihar", "Place of Buddha's Enlightenment.", "UNESCO World Heritage Site.", "Oct-Mar", "5 AM-9 PM", "Free", "https://images.unsplash.com/photo-1561361513-2d000a50f0dc", "Bodh Gaya", "Gaya", "Meditate under Bodhi tree.");

        // --- RAJASTHAN ---
        addPlace(db, "Hawa Mahal", "Rajasthan", "Palace of Breeze.", "Iconic Jaipur landmark.", "Oct-Mar", "9 AM-5 PM", "₹50", "https://images.unsplash.com/photo-1599661046289-e31887846eac", "Jaipur", "Amer Fort", "Visit early morning.");
        addPlace(db, "Amer Fort", "Rajasthan", "Majestic hilltop fort.", "Hindu style elements.", "Oct-Mar", "8 AM-6 PM", "₹100", "https://images.unsplash.com/photo-1590050752117-23a9d7fc21c0", "Jaipur", "Jaigarh Fort", "Enjoy the light show.");

        // --- GOA ---
        addPlace(db, "Baga Beach", "Goa", "Nightlife and water sports hub.", "Famous for shacks.", "Nov-Feb", "24 Hours", "Free", "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2", "North Goa", "Calangute", "Best for night life.");

        // --- KERALA ---
        addPlace(db, "Alleppey Houseboat", "Kerala", "Backwater cruises.", "Venice of the East.", "Sep-Mar", "24 Hours", "Varies", "https://images.unsplash.com/photo-1602216056096-3b40cc0c9944", "Alappuzha", "Kochi", "Overnight stay recommended.");

        // --- JAMMU & KASHMIR ---
        addPlace(db, "Dal Lake", "Jammu and Kashmir", "Jewel of Srinagar.", "Houseboats and Shikaras.", "Apr-Oct", "24 Hours", "Free", "https://images.unsplash.com/photo-1595152230681-34907949667f", "Srinagar", "Pahalgam", "Take a Shikara ride.");

        // --- TAMIL NADU ---
        addPlace(db, "Meenakshi Temple", "Tamil Nadu", "Historic Hindu temple.", "Iconic Gopurams.", "Year round", "5 AM-10 PM", "Free", "https://images.unsplash.com/photo-1582510003544-4d00b7f74220", "Madurai", "Tiruchirappalli", "Traditional dress required.");

        // --- WEST BENGAL ---
        addPlace(db, "Victoria Memorial", "West Bengal", "Large marble building.", "British era museum.", "Oct-Mar", "10 AM-5 PM", "₹30", "https://images.unsplash.com/photo-1558431382-27e303142255", "Kolkata", "Howrah Bridge", "Great for evening walks.");

        // --- DELHI ---
        addPlace(db, "Qutub Minar", "Delhi", "Tallest brick minaret.", "UNESCO site.", "Oct-Mar", "7 AM-9 PM", "₹35", "https://images.unsplash.com/photo-1587474260584-1f20d430c39a", "South Delhi", "Lotus Temple", "Use Metro.");

        // --- PUNJAB ---
        addPlace(db, "Golden Temple", "Punjab", "Holiest Sikh shrine.", "Spiritual center.", "Year round", "24 Hours", "Free", "https://images.unsplash.com/photo-1514222134-b57cbb8ce073", "Amritsar", "Wagah Border", "Eat at Langar.");

        // --- GUJARAT ---
        addPlace(db, "Statue of Unity", "Gujarat", "Tallest statue in the world.", "Memorial to Sardar Patel.", "Oct-Mar", "8 AM-6 PM", "₹150", "https://images.unsplash.com/photo-1627440614110-9aa6939920a0", "Kevadia", "Narmada Dam", "Laser show at evening.");

        // --- MAHARASHTRA ---
        addPlace(db, "Gateway of India", "Maharashtra", "Mumbai landmark.", "Built in 1924.", "Oct-Mar", "24 Hours", "Free", "https://images.unsplash.com/photo-1529253355930-ddbe423a2ac7", "Mumbai", "Marine Drive", "Take a ferry ride.");

        // --- KARNATAKA ---
        addPlace(db, "Mysore Palace", "Karnataka", "Royal residence.", "Palace of Mysore.", "Oct-Mar", "10 AM-5:30 PM", "₹70", "https://images.unsplash.com/photo-1590733115424-64478f7e2c96", "Mysore", "Coorg", "Illuminated on Sundays.");

        // --- TELANGANA ---
        addPlace(db, "Charminar", "Telangana", "16th-century mosque.", "Heart of Hyderabad.", "Oct-Mar", "9 AM-5 PM", "₹25", "https://images.unsplash.com/photo-1574515560126-7c3397940248", "Hyderabad", "Golconda", "Try street food.");

        // --- ASSAM ---
        addPlace(db, "Kaziranga", "Assam", "One-horned rhinos habitat.", "UNESCO Park.", "Nov-Apr", "7 AM-4 PM", "₹100", "https://images.unsplash.com/photo-1571501679680-de32f139268e", "Assam", "Guwahati", "Jeep Safari recommended.");

        // --- SIKKIM ---
        addPlace(db, "Gangtok", "Sikkim", "Hill station capital.", "Beautiful landscapes.", "Oct-Jun", "24 Hours", "Free", "https://images.unsplash.com/photo-1589793907316-f9401541e8f0", "Gangtok", "Pelling", "Visit MG Marg.");

        // --- MADHYA PRADESH ---
        addPlace(db, "Khajuraho", "Madhya Pradesh", "Medieval Hindu temples.", "Famous for carvings.", "Oct-Feb", "6 AM-6 PM", "₹40", "https://images.unsplash.com/photo-1590050752117-23a9d7fc21c0", "Chhatarpur", "Orchha", "Historical tour.");
        
        // --- ODISHA ---
        addPlace(db, "Konark Sun Temple", "Odisha", "Chariot-shaped temple.", "13th-century wonder.", "Oct-Mar", "6 AM-8 PM", "₹40", "https://images.unsplash.com/photo-1605469597401-2c136366838a", "Konark", "Puri", "UNESCO site.");

        // --- ANDHRA PRADESH ---
        addPlace(db, "Tirupati", "Andhra Pradesh", "Lord Venkateswara shrine.", "Seven Hills pilgrimage.", "Year round", "24 Hours", "Varies", "https://images.unsplash.com/photo-1621213079633-87f544606f1b", "Chittoor", "Nellore", "Clean and holy.");

        // --- UTTAR PRADESH ---
        addPlace(db, "Varanasi Ghats", "Uttar Pradesh", "Spiritual center.", "Oldest living city.", "Nov-Feb", "24 Hours", "Free", "https://images.unsplash.com/photo-1561361513-2d000a50f0dc", "Varanasi", "Sarnath", "Attend evening Aarti.");
    }

    private void addPlace(SQLiteDatabase db, String name, String state, String desc, String history, 
                          String bestTime, String timing, String fee, String imageUrl, String loc, 
                          String nearby, String tips) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_STATE, state);
        values.put(COLUMN_DESCRIPTION, desc);
        values.put(COLUMN_HISTORY, history);
        values.put(COLUMN_BEST_TIME, bestTime);
        values.put(COLUMN_TIMING, timing);
        values.put(COLUMN_ENTRY_FEE, fee);
        values.put(COLUMN_IMAGE_URL, imageUrl);
        values.put(COLUMN_LOCATION, loc);
        values.put(COLUMN_NEARBY, nearby);
        values.put(COLUMN_TIPS, tips);
        db.insert(TABLE_PLACES, null, values);
    }

    public List<TouristPlace> getAllPlaces() {
        List<TouristPlace> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PLACES + " ORDER BY " + COLUMN_NAME + " ASC", null);

        if (cursor.moveToFirst()) {
            do {
                TouristPlace place = new TouristPlace(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HISTORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BEST_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMING)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENTRY_FEE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NEARBY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPS))
                );
                list.add(place);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
