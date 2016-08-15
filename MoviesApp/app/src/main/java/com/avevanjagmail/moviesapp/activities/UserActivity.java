package com.avevanjagmail.moviesapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.avevanjagmail.moviesapp.R;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;

/**
 * Created by paulg on 10.08.2016.
 */
public class UserActivity extends AppCompatActivity{
    private ImageView mImageView;
    String url1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mImageView = (ImageView) findViewById(R.id.expandedImage);

        Profile profile = Profile.getCurrentProfile();
        url1 = String.valueOf(profile.getProfilePictureUri(717, 400));
        Log.d("bh", url1);
        Picasso.with(this).load(url1).
                error(R.drawable.ava).resize(717, 400).into(mImageView);


    }

}
