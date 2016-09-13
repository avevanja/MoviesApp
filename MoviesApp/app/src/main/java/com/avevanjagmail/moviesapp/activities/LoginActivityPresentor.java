package com.avevanjagmail.moviesapp.activities;


import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.Interface.LoginActivityView;
import com.avevanjagmail.moviesapp.Interface.LoginApiService;
import com.avevanjagmail.moviesapp.Models.LoginRequest;
import com.avevanjagmail.moviesapp.Models.LoginResponse;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by irabokalo on 09.09.2016.
 */
public class LoginActivityPresentor {
    private LoginActivityView mLoginActivityView;
    private SharedPreferences sPref, sPref1;
    private static final String TAG = LoginActivityPresentor.class.getSimpleName();

    public void setmLoginActivityViewActivityView(LoginActivityView mLoginActivityView) {
        this.mLoginActivityView = mLoginActivityView;
    }

    public SharedPreferences getsPref() {
        return sPref;
    }

    public SharedPreferences getsPref1()

    {
        return sPref1;
    }
    public void doLogin(String login, String password)
    {
        LoginApiService mService = RetrofitUtil.getLoginService();
        final Call<LoginResponse> requestMovie = mService.login(new LoginRequest(login, password));
        requestMovie.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d(TAG, "onResponse - " + response.body().toString());

                if (response.body().getSucceeded().success==true)
                {
                    Toast toast = Toast.makeText(mLoginActivityView.getContext(), "You have logged successfully",Toast.LENGTH_LONG);
                    toast.show();

                    sPref = mLoginActivityView.getContext().getSharedPreferences("SH",mLoginActivityView.getContext().MODE_PRIVATE);
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putString("email", response.body().getData().getEmail());
                    ed.commit();

                    sPref1 = mLoginActivityView.getContext().getSharedPreferences("SH1",mLoginActivityView.getContext().MODE_PRIVATE);
                    SharedPreferences.Editor ed1 = sPref1.edit();
                    ed1.putString("accessToken", response.body().getData().accessToken);
                    ed1.commit();

                    Intent intent = new Intent( mLoginActivityView.getContext(), MainActivity.class );
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mLoginActivityView.getContext().startActivity(intent);


                }
                else if (response.body().getSucceeded().success==false)
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
   public void register()
   {
       Intent intent = new Intent( mLoginActivityView.getContext(), RegistrationActivity.class );
       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       mLoginActivityView.getContext().startActivity(intent);

   }
}
