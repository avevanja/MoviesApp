package com.avevanjagmail.moviesapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.avevanjagmail.moviesapp.Interface.UserActivityView;
import com.avevanjagmail.moviesapp.R;

import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class UserActivity extends AppCompatActivity implements UserActivityView {
    private ImageView ivImage;

    private String email;
    private FloatingActionButton btnSelect;
   private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    SharedPreferences  prefForEmail;
    private TextView  emailText;
    private UserActivityPresentor userActivityPresentor = new UserActivityPresentor();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        emailText = (TextView) findViewById(R.id.email);
        prefForEmail = getSharedPreferences("SH", MODE_PRIVATE);
         email = prefForEmail.getString("email","");
        ivImage = (ImageView) findViewById(R.id.expandedImage);

        userActivityPresentor.setUserActivityView(this);
        userActivityPresentor.setPhoto();
        btnSelect = (FloatingActionButton) findViewById(R.id.change_photo);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userActivityPresentor.selectPhoto();
            }
        });
    }


    @Override
    public TextView getEditEmail() {
        return emailText;
    }

    @Override
    public DatabaseReference getRoot() {
        return mRootRef;
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public ImageView getImage() {
        return  ivImage;
    }

    @Override
    public String getEmail() {
        return  email;
    }
}
