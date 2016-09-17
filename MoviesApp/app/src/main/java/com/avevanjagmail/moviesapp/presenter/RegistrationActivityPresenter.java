package com.avevanjagmail.moviesapp.presenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.avevanjagmail.moviesapp.view.RegistrationActivityView;
import com.avevanjagmail.moviesapp.models.RegisterRequest;
import com.avevanjagmail.moviesapp.models.RegisterResponse;
import com.avevanjagmail.moviesapp.models.VerifyRequest;
import com.avevanjagmail.moviesapp.models.VerifyResponse;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivityPresenter {
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserId = mRootRef.child("Users");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReferenceFromUrl("gs://movies-app-fda81.appspot.com");
    private RegistrationActivityView registrationActivityView;
    private Bitmap thumbnail;


    public void setRegistrationActivityView(RegistrationActivityView registrationActivityView) {
        this.registrationActivityView = registrationActivityView;
    }

    public void doRegisterAndSendVerify(String Name, String Surname, String email, String password, String newPassedArg) {
        RetrofitUtil.getLoginService().register(new RegisterRequest(Name, Surname, email, password, "0"))
                .enqueue(doRegisterAndSendVerify());
        RetrofitUtil.getLoginService().verify(new VerifyRequest(email))
                .enqueue(doVerify());
         mUserId.child(newPassedArg).child("Name").setValue(Name + " " + Surname);

    }

    public void uploadPhoto(Uri tempUri, final String newPassedArg) {
        StorageReference mountainImagesRef = storageRef.child("images/" + tempUri.getLastPathSegment());
        UploadTask uploadTask = mountainImagesRef.putFile(tempUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                e.printStackTrace();
            }
        })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        String Uri = downloadUrl.toString();
                        mUserId.child(newPassedArg).child("Photos").setValue(Uri);

                    }
                });
    }

    private Callback<RegisterResponse> doRegisterAndSendVerify() {
        return new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {


            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                registrationActivityView.failRegistration(t.getCause().toString());

            }
        };
    }

    private Callback<VerifyResponse> doVerify() {
        return new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                registrationActivityView.onSuccessVerify(response.body().getData());

            }

            @Override
            public void onFailure(Call<VerifyResponse> call, Throwable t) {
                registrationActivityView.failVerify(t.getCause().toString());

            }
        };
    }

    public void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
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
        registrationActivityView.setImage(thumbnail);


    }
    @SuppressWarnings("deprecation")
    public void onSelectFromGalleryResult(Intent data) {

        if (data != null) {
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(registrationActivityView.getContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        registrationActivityView.setImage(thumbnail);

    }


}
