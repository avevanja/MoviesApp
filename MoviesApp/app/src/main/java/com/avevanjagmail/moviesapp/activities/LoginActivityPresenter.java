package com.avevanjagmail.moviesapp.activities;


import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.Interface.LoginActivityView;
import com.avevanjagmail.moviesapp.interfaces.LoginApiService;
import com.avevanjagmail.moviesapp.models.LoginRequest;
import com.avevanjagmail.moviesapp.models.LoginResponse;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivityPresenter {
    private LoginActivityView mLoginActivityView;
    private SharedPreferences mSharedPreferences;
    private static final String SHARED = "emailOrId";
    private Profile profile;
    private String mImageUrl;
    private static final String TAG = LoginActivityPresenter.class.getSimpleName();
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserId = mRootRef.child("Users");

    public void setLoginActivityView(LoginActivityView mLoginActivityView) {
        this.mLoginActivityView = mLoginActivityView;
    }

    public void doLogin(String login, String password)
    {
        LoginApiService mService = RetrofitUtil.getLoginService();
        Call<LoginResponse> requestMovie = mService.login(new LoginRequest(login, password));
        requestMovie.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d(TAG, "onResponse - " + response.body().toString());

                if (response.body().getSucceeded().success)
                {
                    Toast toast = Toast.makeText(mLoginActivityView.getContext(), "You have logged successfully",Toast.LENGTH_LONG);
                    toast.show();
                    saveInSharedPreferences(response.body().getData().getEmail(), SHARED);
                    saveInSharedPreferences(response.body().getData().accessToken, "accessToken");
                    mLoginActivityView.onSuccessLogin();

                }
                else if (!response.body().getSucceeded().success)
                {
                    Toast toast = Toast.makeText(mLoginActivityView.getContext(), "Password or Login is incorrect. Try again.",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast toast = Toast.makeText(mLoginActivityView.getContext(), "You failed! Try to check your internet connection", Toast.LENGTH_LONG);
                toast.show();

            }
        });
    }
    public FacebookCallback facebookLogin(){
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                profile = Profile.getCurrentProfile();
                saveInSharedPreferences(profile.getId().toString(), SHARED);
                mImageUrl = String.valueOf(profile.getProfilePictureUri(717, 400));
                mRootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.child("Users").child(profile.getId()).hasChild("need photo")) {
                            mUserId.child(profile.getId()).child("Photos").setValue(mImageUrl);
                            mUserId.child(profile.getId()).child("need photo").setValue("no");
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                mLoginActivityView.onSuccessLoginFaceBook();
            }

            @Override
            public void onCancel() {
                Log.d("On cancel", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("On error", exception.getMessage().toString());
            }
        };

    }
    public void saveInSharedPreferences(String values, String key){
        mSharedPreferences = mLoginActivityView.getContext().getSharedPreferences("SH",mLoginActivityView.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, values);
        editor.commit();
    }

}
