package com.avevanjagmail.moviesapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.avevanjagmail.moviesapp.view.UserActivityView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserActivityPresenter {
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserId = mRootRef.child("Users");
    private SharedPreferences mSharedPreferences;
    private SharedPreferences mPref;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReferenceFromUrl("gs://movies-app-fda81.appspot.com");
    private String passedArg1;
    private String passedArg;
    private Profile profile;
    private UserActivityView mUserActivityView;
    private static final String SHARED = "emailOrId";

    public UserActivityPresenter(UserActivityView mUserActivityView) {
        this.mUserActivityView = mUserActivityView;
    }

    public void downloadPhotoFromFireBase() {
        profile = Profile.getCurrentProfile();

        if (profile != null) {
            setValueEventListener(profile.getId());
        } else {
            setValueEventListener(getChildFromShared());
        }
    }

    public String getChildFromShared() {
        mPref = mUserActivityView.getContext().getSharedPreferences("SH", mUserActivityView.getContext().MODE_PRIVATE);
        passedArg1 = mPref.getString(SHARED, "");
        passedArg = passedArg1.replace(".", "a");
        return passedArg;
    }

    public void setValueEventListener(final String child) {
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String myUrl = dataSnapshot.child("Users").child(child).child("Photos").getValue().toString();
                if (profile == null) {
                    mUserActivityView.setName(dataSnapshot.child("Users").child(child).child("Name").getValue().toString());
                    saveInSharedPreferences(dataSnapshot.child("Users").child(child).child("Name").getValue().toString(), "name");

                }
                else {
                    mUserActivityView.setName(profile.getFirstName() + " " + profile.getLastName());
                    saveInSharedPreferences(profile.getFirstName() + " " + profile.getLastName(), "name");
                }
                Uri myUri = Uri.parse(myUrl);
                mUserActivityView.setUrl(myUri);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fileOutputStream;
        try {
            destination.createNewFile();
            fileOutputStream = new FileOutputStream(destination);
            fileOutputStream.write(bytes.toByteArray());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mUserActivityView.setBm(thumbnail);

        Uri tempUri = getImageUri(mUserActivityView.getContext(), thumbnail);
        uploadPhoto(tempUri);

    }

    @SuppressWarnings("deprecation")
    public void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(mUserActivityView.getContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mUserActivityView.setBm(bm);

        Uri tempUri = getImageUri(mUserActivityView.getContext(), bm);
        uploadPhoto(tempUri);

    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void uploadPhoto(Uri tempUri) {
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
                        if (profile != null) {
                            mUserId.child(profile.getId()).child("Photos").setValue(Uri);
                        } else {
                            mUserId.child(getChildFromShared()).child("Photos").setValue(Uri);
                        }

                    }
                });
    }

    private void saveInSharedPreferences(String values, String key) {
        mSharedPreferences = mUserActivityView.getContext().getSharedPreferences("SHNAME", mUserActivityView.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, values);
        editor.commit();
    }

    public void getUserName() {
        mPref = mUserActivityView.getContext().getSharedPreferences("SHNAME", mUserActivityView.getContext().MODE_PRIVATE);
        mUserActivityView.setName(mPref.getString("name", " no name"));
    }
    public void onDestroy(){
        mUserActivityView = null;
    }

}



