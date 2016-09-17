package com.avevanjagmail.moviesapp.presenter;


import android.content.SharedPreferences;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.interfaces.LoginApiService;
import com.avevanjagmail.moviesapp.models.LogOutRequest;
import com.avevanjagmail.moviesapp.models.LogoutResponse;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.avevanjagmail.moviesapp.view.MainActivityView;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityPresenter {
    private SharedPreferences mPref;
    private SharedPreferences mPref1;
    private MainActivityView mMainActivityView;
    private SharedPreferences mSharedPreferences;
    private Profile profile;
    private String mImageUrl;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserId = mRootRef.child("Users");
    private static final String SHARED = "emailOrId";

    public void setMainActivityView(MainActivityView mMainActivityView) {
        this.mMainActivityView = mMainActivityView;
    }

    public void logout() {

        LoginApiService mService = RetrofitUtil.getLoginService();
        Call<LogoutResponse> requestInfo = mService.logout(new LogOutRequest(getParametersForLogOut()[0], getParametersForLogOut()[1]));
        requestInfo.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {

                SharedPreferences.Editor ed = mPref.edit();
                ed.clear();
                ed.commit();
                mMainActivityView.setLogOut(response.body().getSucceeded().success);
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {

                Toast.makeText(mMainActivityView.getContext(), "Check your Internet connection", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void logOutFromFB() {
        mPref = mMainActivityView.getContext().getSharedPreferences("SH", mMainActivityView.getContext().MODE_PRIVATE);
        SharedPreferences.Editor ed = mPref.edit();
        ed.clear();
        ed.commit();
        FacebookSdk.sdkInitialize(mMainActivityView.getContext());
        LoginManager.getInstance().logOut();


    }

    public String[] getParametersForLogOut() {
        mPref = mMainActivityView.getContext().getSharedPreferences("SH", mMainActivityView.getContext().MODE_PRIVATE);
        String passedArg = mPref.getString(SHARED, "");
        mPref1 = mMainActivityView.getContext().getSharedPreferences("SH", mMainActivityView.getContext().MODE_PRIVATE);
        String accessToken = mPref1.getString("accessToken", "");
        String[] sListParameters = new String[2];
        sListParameters[0] = passedArg;
        sListParameters[1] = accessToken;
        return sListParameters;
    }

    public void saveFaceBook() {
        profile = Profile.getCurrentProfile();

        saveInSharedPreferences(profile.getId().toString(), SHARED);
        mImageUrl = String.valueOf(profile.getProfilePictureUri(717, 400));
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("Users").child(profile.getId()).hasChild("Photos")) {
                    mUserId.child(profile.getId()).child("Photos").setValue(mImageUrl);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

   private void saveInSharedPreferences(String values, String key) {
        mSharedPreferences = mMainActivityView.getContext().getSharedPreferences("SH", mMainActivityView.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, values);
        editor.commit();
    }
}
