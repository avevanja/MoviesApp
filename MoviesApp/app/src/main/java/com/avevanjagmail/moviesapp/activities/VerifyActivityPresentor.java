package com.avevanjagmail.moviesapp.activities;

import android.content.Intent;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.Interface.LoginApiService;
import com.avevanjagmail.moviesapp.Interface.VerifyActivityView;
import com.avevanjagmail.moviesapp.Models.ActivateRequest;
import com.avevanjagmail.moviesapp.Models.ActivateResponse;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by irabokalo on 10.09.2016.
 */
public class VerifyActivityPresentor {
  private VerifyActivityView verifyActivityView;

    public void setVerifyActivityView(VerifyActivityView verifyActivityView) {
        this.verifyActivityView = verifyActivityView;
    }

    public void verify(String emailT, String code)
    {
        LoginApiService mService = RetrofitUtil.getLoginService();
        Call<ActivateResponse> requestInfo = mService.activate( new ActivateRequest( code, emailT ) );
        requestInfo.enqueue( new Callback<ActivateResponse>() {
            @Override
            public void onResponse(Call<ActivateResponse> call, Response<ActivateResponse> response) {
                System.out.println( response.body().getSucceeded().message );

                if (response.body().getSucceeded().success == true)
                {
                    Toast toast = Toast.makeText(verifyActivityView.getContext(), "Congratulations you activated your account!",Toast.LENGTH_LONG);
                    toast.show();
                    Intent myIntent = new Intent(verifyActivityView.getContext(),LoginActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    verifyActivityView.getContext().startActivity(myIntent);
                }

            }

            @Override
            public void onFailure(Call<ActivateResponse> call, Throwable t) {
                System.out.println( "Errror" );
            }
        } );
    }
}
