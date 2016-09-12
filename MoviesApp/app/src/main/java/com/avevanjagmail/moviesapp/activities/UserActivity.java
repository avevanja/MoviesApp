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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


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



public class UserActivity extends AppCompatActivity {
    private ImageView ivImage;
    private Profile profile;
    private FloatingActionButton btnSelect;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserId = mRootRef.child("Users");
    SharedPreferences mPref, prefForUser, prefForEmail;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://movies-app-fda81.appspot.com");
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private TextView info, emailText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);

        emailText = (TextView) findViewById(R.id.email);

        prefForEmail = getSharedPreferences("SH", MODE_PRIVATE);

        String email = prefForEmail.getString("saved_text","");


        emailText.setText(email);

        ivImage = (ImageView) findViewById(R.id.movie_poster_iv);
        FacebookSdk.sdkInitialize(getApplicationContext());
         profile = Profile.getCurrentProfile();

if (profile!=null)
{
    mRootRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            String myUrl = dataSnapshot.child("Users").child(profile.getId()).child("Photos").getValue().toString();
            Uri myUri = Uri.parse(myUrl);
            Picasso.with(getApplicationContext()).load(myUri).fit().centerCrop().into(ivImage);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
        }
        else
        {
            mRootRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    mPref = getSharedPreferences("SH", MODE_PRIVATE);
                    String passedArg1 = mPref.getString("saved_text", "");
                    final String passedArg = passedArg1.replace(".", "a");
                    String myUrl = dataSnapshot.child("Users").child(passedArg).child("Photos").getValue().toString();
                  try {
                      Uri myUri = Uri.parse(myUrl);
                      Picasso.with(getApplicationContext()).load(myUri).fit().centerCrop().into(ivImage);
                      Log.d("ura", myUri.toString());
                  }
                  catch (Exception e)
                  {
                      System.out.print(e.getCause());
                  }

                }
                @Override
                public void onCancelled(DatabaseError databaseError)
                {

                }
            });
        }
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
//        builder.setTitle("Add Photo!");
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
        intent.setAction(Intent.ACTION_GET_CONTENT);//
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
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPref = getSharedPreferences("SH", MODE_PRIVATE);
        String passedArg1 = mPref.getString("saved_text", "");
        final String passedArg = passedArg1.replace(".", "a");
        ivImage.setImageBitmap(thumbnail);
        Uri tempUri = getImageUri(getApplicationContext(), thumbnail);

        StorageReference mountainImagesRef = storageRef.child("images/" + tempUri.getLastPathSegment());
        UploadTask uploadTask = mountainImagesRef.putFile(tempUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        String Uri = downloadUrl.toString();
                        if (profile!= null)
                        {
                            mUserId.child(profile.getId()).child("Photos").setValue(Uri);
                        }
                        else
                        {
                            mUserId.child(passedArg).child("Photos").setValue(Uri);
                        }

                    }
                });

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ivImage.setImageBitmap(bm);
        mPref = getSharedPreferences("SH", MODE_PRIVATE);
        String passedArg1 = mPref.getString("saved_text", "");
        final String passedArg = passedArg1.replace(".", "a");
        Uri tempUri = getImageUri(getApplicationContext(), bm);


        StorageReference mountainImagesRef = storageRef.child("images/" + tempUri.getLastPathSegment());
        UploadTask uploadTask = mountainImagesRef.putFile(tempUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        String Uri = downloadUrl.toString();
                        if (profile!= null)
                        {
                            mUserId.child(profile.getId()).child("Photos").setValue(Uri);
                        }
                        else
                        {
                            mUserId.child(passedArg).child("Photos").setValue(Uri);
                        }



                    }
                });
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
