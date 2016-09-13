package com.avevanjagmail.moviesapp.Interface;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by irabokalo on 10.09.2016.
 */
public interface UserActivityView {

    TextView getEditEmail();
    DatabaseReference getRoot();
    Context getContext();
    ImageView getImage();
    String getEmail();
}
