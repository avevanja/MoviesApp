package com.avevanjagmail.moviesapp.presenter;


import android.content.SharedPreferences;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.activities.LoginActivity;
import com.avevanjagmail.moviesapp.activities.SearchActivity;
import com.avevanjagmail.moviesapp.interfaces.LoginApiService;
import com.avevanjagmail.moviesapp.models.LogOutRequest;
import com.avevanjagmail.moviesapp.models.LogoutResponse;
import com.avevanjagmail.moviesapp.utils.ConnectivityUtility;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.avevanjagmail.moviesapp.utils.SharedPreferencesUtility;
import com.avevanjagmail.moviesapp.view.MainActivityView;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityPresenter {
    private static final String TAG = MainActivityPresenter.class.getSimpleName();
    private MainActivityView mMainActivityView;
    private SharedPreferences mSharedPreferences;
    private Profile profile;
    private String mImageUrl;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserId = mRootRef.child("Users");
    private static final String SHARED = "emailOrId";
    private SharedPreferencesUtility mSharedPreferencesUtility = new SharedPreferencesUtility();

    public void setMainActivityView(MainActivityView mMainActivityView) {
        this.mMainActivityView = mMainActivityView;
    }

    public void logOut() {
        if (ConnectivityUtility.isOnline(mMainActivityView.getContext())) {


            profile = Profile.getCurrentProfile();
            if (profile == null) {
                logout();


            } else {
                logOutFromFB();
                LoginActivity.start(mMainActivityView.getContext());
                mMainActivityView.finishLogOut();
            }
        } else {
            Toast.makeText(mMainActivityView.getContext(), R.string.error_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void logout() {

        LoginApiService mService = RetrofitUtil.getLoginService();
        Call<LogoutResponse> requestInfo = mService.logout(new LogOutRequest(getParametersForLogOut()[0], getParametersForLogOut()[1]));
        requestInfo.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.body().getSucceeded().success) {
                    mSharedPreferencesUtility.clearSharedPreferences(mMainActivityView.getContext());
                    LoginActivity.start(mMainActivityView.getContext());
                    mMainActivityView.finishLogOut();
                } else {
                    Toast.makeText(mMainActivityView.getContext(), R.string.error_logout, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {

                Toast.makeText(mMainActivityView.getContext(), "Check your Internet connection", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void logOutFromFB() {
        mSharedPreferencesUtility.clearSharedPreferences(mMainActivityView.getContext());
        LoginManager.getInstance().logOut();


    }

    public String[] getParametersForLogOut() {
        String[] sListParameters = new String[2];
        sListParameters[0] = mSharedPreferencesUtility.getSPref(mMainActivityView.getContext(), SHARED);
        sListParameters[1] = mSharedPreferencesUtility.getSPref(mMainActivityView.getContext(), "accessToken");
        return sListParameters;
    }

//    public void saveFaceBook() {
//
//
//            profile = Profile.getCurrentProfile();
//
//
//            if (profile != null) {
//                Log.d("LOGIN", "saveFaceBook: " + profile.getId());
//                mSharedPreferencesUtility.addToShared(mMainActivityView.getContext(), SHARED, profile.getId());
//                mImageUrl = String.valueOf(profile.getProfilePictureUri(717, 400));
//                mRootRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (!dataSnapshot.child("Users").child(profile.getId()).hasChild("Photos")) {
//                            mUserId.child(profile.getId()).child("Photos").setValue(mImageUrl);
//                        }
//                    }
//
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//
//                });
//
//            } else {
//                Log.d("LOGIN", "saveFaceBook: profile is null");
//            }
//
//
//    }


    public void startSearch(String query) {
        SearchActivity.start(mMainActivityView.getContext(), query);
    }
    public void onDestroy(){
        mMainActivityView = null;
    }
}
