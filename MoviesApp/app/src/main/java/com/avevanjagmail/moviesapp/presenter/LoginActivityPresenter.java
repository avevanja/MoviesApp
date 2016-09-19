package com.avevanjagmail.moviesapp.presenter;


import android.util.Log;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.activities.MainActivity;
import com.avevanjagmail.moviesapp.activities.RegistrationActivity;
import com.avevanjagmail.moviesapp.models.LoginRequest;
import com.avevanjagmail.moviesapp.models.LoginResponse;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.avevanjagmail.moviesapp.utils.SharedPreferencesUtility;
import com.avevanjagmail.moviesapp.view.LoginActivityView;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivityPresenter {
    private LoginActivityView mLoginActivityView;
    private static final String SHARED = "emailOrId";
    private static final String TAG = LoginActivityPresenter.class.getSimpleName();
    private SharedPreferencesUtility mSharedPreferencesUtility = new SharedPreferencesUtility();
    private Profile profile;

    public void setLoginActivityView(LoginActivityView mLoginActivityView) {
        this.mLoginActivityView = mLoginActivityView;
    }

    public void doLogin(String login, String password) {
        boolean error = false;
        if (login.length() == 0) {
            mLoginActivityView.errorLogin("please write email");
            error = true;
        }
        if (password.length() == 0) {
            mLoginActivityView.errorPassword("please write password");
            error = true;
        }
        if (!error) {

            RetrofitUtil
                    .getLoginService()
                    .login(new LoginRequest(login, password))
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.body().getSucceeded().success) {
                                Toast toast = Toast.makeText(mLoginActivityView.getContext(), "You have logged successfully", Toast.LENGTH_LONG);
                                toast.show();
                                mSharedPreferencesUtility.addToShared(mLoginActivityView.getContext(), SHARED, response.body().getData().getEmail());
                                mSharedPreferencesUtility.addToShared(mLoginActivityView.getContext(), "accessToken", response.body().getData().accessToken);
//                    saveInSharedPreferences(response.body().getData().getEmail(), SHARED);
//                    saveInSharedPreferences(response.body().getData().accessToken, "accessToken");
                                startMainActivity();
                                mLoginActivityView.onSuccessLogin();

                            } else if (!response.body().getSucceeded().success) {
                                Toast toast = Toast.makeText(mLoginActivityView.getContext(), "Password or Login is incorrect. Try again.", Toast.LENGTH_LONG);
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
//        LoginApiService mService = RetrofitUtil.getLoginService();
//        Call<LoginResponse> requestMovie = mService.login(new LoginRequest(login, password));
//        requestMovie.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                Log.d(TAG, "onResponse - " + response.body().toString());
//
//                if (response.body().getSucceeded().success) {
//                    Toast toast = Toast.makeText(mLoginActivityView.getContext(), "You have logged successfully", Toast.LENGTH_LONG);
//                    toast.show();
//                    mSharedPreferencesUtility.addToShared(mLoginActivityView.getContext(), SHARED,response.body().getData().getEmail());
////                    saveInSharedPreferences(response.body().getData().getEmail(), SHARED);
////                    saveInSharedPreferences(response.body().getData().accessToken, "accessToken");
//                    startMainActivity();
//                    mLoginActivityView.onSuccessLogin();
//
//                } else if (!response.body().getSucceeded().success) {
//                    Toast toast = Toast.makeText(mLoginActivityView.getContext(), "Password or Login is incorrect. Try again.", Toast.LENGTH_LONG);
//                    toast.show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Toast toast = Toast.makeText(mLoginActivityView.getContext(), "You failed! Try to check your internet connection", Toast.LENGTH_LONG);
//                toast.show();
//
//            }
//        });
    }

    public FacebookCallback<LoginResult> facebookLogin() {
        return new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
//                profile = Profile.getCurrentProfile();
//                if (profile != null) {
//                    Log.d("LOGIN", "saveFaceBook: " + profile.getId());
//                    mSharedPreferencesUtility.addToShared(mLoginActivityView.getContext(), SHARED, profile.getId());
//
//                } else {
//                    Log.d("LOGIN", "saveFaceBook: profile is null");
//                }

                startMainActivity();
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

//    public void saveInSharedPreferences(String values, String key) {
//        mSharedPreferences = mLoginActivityView.getContext().getSharedPreferences("SH", mLoginActivityView.getContext().MODE_PRIVATE);
//        SharedPreferences.Editor editor = mSharedPreferences.edit();
//        editor.putString(key, values);
//        editor.commit();
//    }

    public void startRegistrationActivity() {
        RegistrationActivity.start(mLoginActivityView.getContext());
        mLoginActivityView.onSuccessLogin();
    }

    public void startMainActivity() {
        MainActivity.start(mLoginActivityView.getContext());
    }

    public void checkLogin() {
        boolean checkout;
        Log.d("LOGIN", "checkLogin: userId " + mSharedPreferencesUtility.getmSharedPreferences(mLoginActivityView.getContext()).getString(SHARED, ""));
        checkout = mSharedPreferencesUtility.getmSharedPreferences(mLoginActivityView.getContext()).contains(SHARED);
        if (checkout) {
            startMainActivity();
            mLoginActivityView.onSuccessLogin();
        }
    }
    public void onDestroy(){
        mLoginActivityView = null;
    }

}
