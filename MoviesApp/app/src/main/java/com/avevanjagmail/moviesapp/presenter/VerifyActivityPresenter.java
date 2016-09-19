package com.avevanjagmail.moviesapp.presenter;

import android.widget.Toast;

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.activities.LoginActivity;
import com.avevanjagmail.moviesapp.models.ActivateRequest;
import com.avevanjagmail.moviesapp.models.ActivateResponse;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.avevanjagmail.moviesapp.view.VerifyActivityView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerifyActivityPresenter {
    private VerifyActivityView verifyActivityView;

    public void setVerifyActivityView(VerifyActivityView verifyActivityView) {
        this.verifyActivityView = verifyActivityView;
    }

    public void verify(String emailT, String code) {
        RetrofitUtil.getLoginService().activate(new ActivateRequest(code, emailT)).enqueue(new Callback<ActivateResponse>() {
            @Override
            public void onResponse(Call<ActivateResponse> call, Response<ActivateResponse> response) {

                if (response.body().getSucceeded().success) {
                    //                   verifyActivityView.succeededVerify();
                    LoginActivity.start(verifyActivityView.getContext());
                    Toast toast = Toast.makeText(verifyActivityView.getContext(), R.string.hint_account_activate, Toast.LENGTH_LONG);
                    toast.show();

                }
            }

            @Override
            public void onFailure(Call<ActivateResponse> call, Throwable t) {

            }
        });

    }
    public void onDestroy(){
        verifyActivityView = null;
    }
}
