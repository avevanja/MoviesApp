package com.avevanjagmail.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avevanjagmail.moviesapp.Interface.LoginApiService;
import com.avevanjagmail.moviesapp.Models.LoginRequest;
import com.avevanjagmail.moviesapp.Models.LoginResponse;
import com.avevanjagmail.moviesapp.Models.RegisterRequest;
import com.avevanjagmail.moviesapp.Models.RegisterResponse;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by John on 08.07.2016.
 */
public class RegistrationActivity extends AppCompatActivity{
    final String TAG = LoginActivity.class.getSimpleName();
    EditText fName, lName, email, password;
    Button btn_create;
    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
       fName = (EditText) findViewById(R.id.firstName);
        lName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btn_create = (Button)(findViewById(R.id.btn_create));
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginApiService mService = RetrofitUtil.getLoginService();
                Call<RegisterResponse> requestMovie = mService.register(new RegisterRequest(fName.getText().toString(),
                        lName.getText().toString(),email.getText().toString(),password.getText().toString()));
                requestMovie.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        Log.d(TAG, "onResponse - " + response.body().toString());
                           if (response.body().getSucceeded().success == true)
                         {

                            System.out.println("hahahah");
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        System.out.println("hahahah1");
                    }
                });
            }
        });

    }
}

