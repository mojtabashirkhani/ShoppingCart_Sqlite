package com.nikan.clickpaz.nikantest.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by slim shady on 09/18/2018.
 */

public class InfoPref {

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "InfoPrefrence";

    private static final String KEY_FIRST = "first";
    private static final String KEY_LAST = "last";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_LAND = "land";

    public InfoPref(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstName(String firstName){

        editor.putString(KEY_FIRST, firstName);
        editor.commit();
    }

    public void setLastName(String lastName){

        editor.putString(KEY_LAST, lastName);
        editor.commit();
    }

    public void setAddress(String address){

        editor.putString(KEY_ADDRESS, address);
        editor.commit();
    }

    public void setLand(String land){

        editor.putString(KEY_LAND, land);
        editor.commit();
    }

    public String getFirstName()
    {
        return pref.getString(KEY_FIRST, null);
    }
    public String getLastName(){

        return pref.getString(KEY_LAST, null);

    }

    public String getAddress(){

        return pref.getString(KEY_ADDRESS, null);

    }

    public String getLand(){

        return pref.getString(KEY_LAND, null);

    }

    public void clearInfo(){
        editor.clear();
        editor.commit();
    }


}
