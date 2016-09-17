package com.avevanjagmail.moviesapp;

import android.content.Context;
import android.net.ConnectivityManager;


public class ConnectivityReceiver { //в Утиліти -_-

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}