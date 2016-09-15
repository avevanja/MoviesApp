package com.avevanjagmail.moviesapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.presenter.UserActivityPresenter;
import com.avevanjagmail.moviesapp.view.UserActivityView;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;


public class UserActivity extends AppCompatActivity implements UserActivityView {
    private ImageView ivImage;
    private FloatingActionButton btnSelect;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask, email;
    private TextView info, emailText;
    private Profile profile;
    private UserActivityPresenter mUserActivityPresenter;
    private Toolbar mToolbarInformActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_user);
        profile = Profile.getCurrentProfile();
        emailText = (TextView) findViewById(R.id.name_user_tv);

        ivImage = (ImageView) findViewById(R.id.expandedImage);
        mToolbarInformActivity = (Toolbar) findViewById(R.id.toolbar_inf_act1);
        setSupportActionBar(mToolbarInformActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarInformActivity.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setTitle("Setting");
        mUserActivityPresenter = new UserActivityPresenter(this);
        mUserActivityPresenter.downloadPhotoFromFireBase();

        btnSelect = (FloatingActionButton) findViewById(R.id.change_photo);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case com.avevanjagmail.moviesapp.utils.Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = com.avevanjagmail.moviesapp.utils.Utility.checkPermission(getApplicationContext());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                mUserActivityPresenter.onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                mUserActivityPresenter.onCaptureImageResult(data);
        }
    }


    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void setUrl(Uri uri) {
        Picasso.with(getApplicationContext()).load(uri).fit().centerCrop().into(ivImage);
    }

    @Override
    public void setBm(Bitmap bitmap) {
        ivImage.setImageBitmap(bitmap);
    }

    @Override
    public void setName(String name) {
        if (profile == null) {
            email = name;
        } else {
            email = profile.getFirstName() + " " + profile.getLastName();
        }
        emailText.setText(email);
    }
}
