package com.avevanjagmail.moviesapp.activities;

import com.avevanjagmail.moviesapp.Interface.VerifyActivityView;
import com.avevanjagmail.moviesapp.interfaces.LoginApiService;
import com.avevanjagmail.moviesapp.models.ActivateRequest;
import com.avevanjagmail.moviesapp.models.ActivateResponse;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerifyActivityPresenter {
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

                if (response.body().getSucceeded().success)
                {
                    verifyActivityView.succeededVerify();
                }

            }

            @Override
            public void onFailure(Call<ActivateResponse> call, Throwable t) {
            }
        } );
    }
}
