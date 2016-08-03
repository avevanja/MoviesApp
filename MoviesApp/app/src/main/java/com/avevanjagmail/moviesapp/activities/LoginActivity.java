package com.avevanjagmail.moviesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.Interface.LoginApiService;
import com.avevanjagmail.moviesapp.Models.LoginRequest;
import com.avevanjagmail.moviesapp.Models.LoginResponse;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    TextView tvReg;
    Button btnLog;
    EditText email, password1;
    private final String URL = "http://146.185.180.39:4020/login/email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String TAG = LoginActivity.class.getSimpleName();


        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_login);

        tvReg = (TextView) findViewById(R.id.tV_reg);
        email = (EditText) findViewById(R.id.lastName);
        password1 = (EditText) findViewById(R.id.email)
        ;
        btnLog = (Button) findViewById(R.id.btn_create);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginApiService mService = RetrofitUtil.getLoginService();
                String login = email.getText().toString();
                String password = password1.getText().toString();
                Call<LoginResponse> requestMovie = mService.login(new LoginRequest(login, password));
                requestMovie.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        Log.d(TAG, "onResponse - " + response.body().toString());

                        if (response.body().getSucceeded().success==true)
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "You have logged successfully",Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                            startActivity(intent);
                        }
                        else if (response.body().getSucceeded().success==false)
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Password or Login is incorrect. Try again.",Toast.LENGTH_LONG);
                            toast.show();

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast toast = Toast.makeText(getApplicationContext(), "You failed! Try to check your internet connection",Toast.LENGTH_LONG);
                        toast.show();

                    }
                });
            }
        });
      tvReg.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent( getApplicationContext(), RegistrationActivity.class );
              startActivity(intent);
          }
      });


            }
        }











  /*  public void onClick1(View view) {
        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);


    }*/

