package com.avevanjagmail.moviesapp;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by paulg on 30.08.2016.
 */
public class ConnectivityReceiver {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}