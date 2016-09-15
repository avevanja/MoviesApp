package com.avevanjagmail.moviesapp.activities;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.avevanjagmail.moviesapp.Interface.RegistrationActivityView;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivityPresenter {
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserId = mRootRef.child("Users");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReferenceFromUrl("gs://movies-app-fda81.appspot.com");
    private RegistrationActivityView registrationActivityView;

    public void setRegistrationActivityView(RegistrationActivityView registrationActivityView) {
        this.registrationActivityView = registrationActivityView;
    }

    public void doRegisterAndSendVerify(String Name, String Surname, String email, String password) {
        RetrofitUtil.getLoginService().register(new RegisterRequest(Name, Surname, email, password, "0"))
                .enqueue(doRegisterAndSendVerify());
//        LoginApiService mService = RetrofitUtil.getLoginService();
//        Call<RegisterResponse> requestMovie = mService.register(new RegisterRequest(Name, Surname, email, password, "0"));
//        requestMovie.enqueue(new Callback<RegisterResponse>() {
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                Toast toast = Toast.makeText(registrationActivityView.getContext(), t.getCause().toString(), Toast.LENGTH_LONG);
//                toast.show();
//            }
//        });
        RetrofitUtil.getLoginService().verify(new VerifyRequest(email))
                .enqueue(doVerify());
//        Call<VerifyResponse> requestInfo = mService.verify(new VerifyRequest(email));
//        requestInfo.enqueue(new Callback<VerifyResponse>() {
//
//
//            @Override
//            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
//                if (response.body().getSucceeded().success) {
//                    Intent myIntent = new Intent(registrationActivityView.getContext(), VerifyActivity.class).putExtra("email", response.body().getData());
//                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    registrationActivityView.getContext().startActivity(myIntent);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<VerifyResponse> call, Throwable t) {
//                Toast toast = Toast.makeText(registrationActivityView.getContext(), t.getCause().toString(), Toast.LENGTH_LONG);
//                toast.show();
//            }
//        });
    }

    public void uploadPhoto(Uri tempUri, final String newPassedArg) {
                StorageReference mountainImagesRef = storageRef.child("images/" + tempUri.getLastPathSegment());
        //                    progressDialog.dismiss();
//                    progressDialog.show();
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
   private Callback<RegisterResponse> doRegisterAndSendVerify(){
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
    private Callback<VerifyResponse> doVerify(){
        return new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
//                registrationActivityView.setEmail(response.body().getData());
                registrationActivityView.onSuccessVerify(response.body().getData());

            }

            @Override
            public void onFailure(Call<VerifyResponse> call, Throwable t) {
                registrationActivityView.failVerify(t.getCause().toString());

            }
        };
    }

}
