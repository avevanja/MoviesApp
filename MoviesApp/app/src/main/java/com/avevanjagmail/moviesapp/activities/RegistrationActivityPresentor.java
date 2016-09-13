package com.avevanjagmail.moviesapp.activities;

import android.content.Intent;
import android.widget.Toast;
import com.avevanjagmail.moviesapp.Interface.LoginApiService;
import com.avevanjagmail.moviesapp.Interface.RegistrationActivityView;
import com.avevanjagmail.moviesapp.Models.RegisterRequest;
import com.avevanjagmail.moviesapp.Models.RegisterResponse;
import com.avevanjagmail.moviesapp.Models.VerifyRequest;
import com.avevanjagmail.moviesapp.Models.VerifyResponse;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by irabokalo on 10.09.2016.
 */
public class RegistrationActivityPresentor {
    private RegistrationActivityView registrationActivityView;
    public void setRegistrationActivityView(RegistrationActivityView registrationActivityView) {
        this.registrationActivityView = registrationActivityView;
    }

    public void doRegister(String Name, String Surname, String email, String password)
    {
        LoginApiService mService = RetrofitUtil.getLoginService();


        Call<RegisterResponse> requestMovie = mService.register(new RegisterRequest(Name,Surname,email,password, "0"));

        requestMovie.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast toast = Toast.makeText(registrationActivityView.getContext(),t.getCause().toString(),Toast.LENGTH_LONG);
                toast.show();
            }
        });
        Call<VerifyResponse> requestInfo = mService.verify(new VerifyRequest(email));
        requestInfo.enqueue(new Callback<VerifyResponse>() {


            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                if (response.body().getSucceeded().success==true)
                {

                    Intent myintent = new Intent(registrationActivityView.getContext(), VerifyActivity.class).putExtra("email", response.body().getData());
                    myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   registrationActivityView.getContext().startActivity(myintent);
                }

            }

            @Override
            public void onFailure(Call<VerifyResponse> call, Throwable t) {
                Toast toast = Toast.makeText(registrationActivityView.getContext(),t.getCause().toString(),Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
