package com.avevanjagmail.moviesapp.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtility {
    private SharedPreferences mSharedPreferences;
    private String valuesShared;
    public void addToShared(Context context, String key, String values){
        mSharedPreferences = context.getSharedPreferences("SH",context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = mSharedPreferences.edit();
//        editor.putString(key, values);
//        editor.commit();
        mSharedPreferences.edit()
                .putString(key, values)
                .commit();

    }
    public String getSPref(Context context, String key) {
        mSharedPreferences = context.getSharedPreferences("SH", context.MODE_PRIVATE);
        valuesShared = mSharedPreferences.getString(key, "");
        return valuesShared;
    }
    public SharedPreferences getmSharedPreferences(Context context){
       return mSharedPreferences = context.getSharedPreferences("SH", context.MODE_PRIVATE);
    }
    public void clearSharedPreferences (Context context){
        mSharedPreferences = context.getSharedPreferences("SH", context.MODE_PRIVATE);
//        SharedPreferences.Editor ed = mPref.edit();
//        ed.clear();
//        ed.commit();
        mSharedPreferences.edit()
                .clear()
                .commit();
    }
}
