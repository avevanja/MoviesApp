package com.avevanjagmail.moviesapp.managers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import static com.facebook.FacebookSdk.getApplicationContext;


public class AlertDialogManager {
    public AlertDialog.Builder createDialog(Context context, DialogInterface.OnClickListener listener){
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(items, listener)
                .show();
        return builder;
    }
}
