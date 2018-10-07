package com.nikan.clickpaz.nikantest.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by slim shady on 09/16/2018.
 */

public class LocationPref {

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LocationPrefrence";

    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";

    public LocationPref(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLat(String lat){
        editor.putString(KEY_LAT, lat);
        editor.commit();

    }

    public void setLng(String lng){
        editor.putString(KEY_LNG, lng);
        editor.commit();
    }

    public String getLat(){
        return pref.getString(KEY_LAT, null);
    }
    public String getLng(){
        return pref.getString(KEY_LNG, null);

    }

    public void clearLocation(){
        editor.clear();
        editor.commit();
    }



}
