package com.avevanjagmail.moviesapp.presenter;



import android.content.SharedPreferences;
import android.widget.Toast;
import com.avevanjagmail.moviesapp.interfaces.LoginApiService;
import com.avevanjagmail.moviesapp.models.LogOutRequest;
import com.avevanjagmail.moviesapp.models.LogoutResponse;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.avevanjagmail.moviesapp.view.MainActivityView;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityPresenter {
    private SharedPreferences mPref;
    private SharedPreferences mPref1;
    private MainActivityView mMainActivityView;

    public void setMainActivityView(MainActivityView mMainActivityView) {
        this.mMainActivityView = mMainActivityView;
    }

    public void logout() {

        LoginApiService mService = RetrofitUtil.getLoginService();
        Call<LogoutResponse> requestInfo = mService.logout(new LogOutRequest(getParametersForLogOut()[0], getParametersForLogOut()[1]));
        requestInfo.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                mMainActivityView.setLogOut(response.body().getSucceeded().success);
                SharedPreferences.Editor ed = mPref.edit();
                ed.clear();
                ed.commit();
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {

                Toast.makeText(mMainActivityView.getContext(), "Check your Internet connection", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void logOutFromFB() {
        FacebookSdk.sdkInitialize(mMainActivityView.getContext());
        LoginManager.getInstance().logOut();

    }
    public String[] getParametersForLogOut(){
        mPref = mMainActivityView.getContext().getSharedPreferences("SH", mMainActivityView.getContext().MODE_PRIVATE);
        String passedArg = mPref.getString("saved_text", "");
        mPref1 = mMainActivityView.getContext().getSharedPreferences("SH1", mMainActivityView.getContext().MODE_PRIVATE);
        String accessToken = mPref1.getString("saved_text1", "");
        String[] sListParameters = new String[2];
        sListParameters[0] = passedArg;
        sListParameters[1] = accessToken;
        return sListParameters;
    }
}
