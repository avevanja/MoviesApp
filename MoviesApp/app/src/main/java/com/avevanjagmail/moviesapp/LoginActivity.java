package com.avevanjagmail.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avevanjagmail.moviesapp.Interface.LoginApiService;
import com.avevanjagmail.moviesapp.Models.LoginRequest;
import com.avevanjagmail.moviesapp.Models.LoginResponse;
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
        TextView tvReg;
        Button btnLog;
        EditText email, password1;
        final String URL = "http://146.185.180.39:4020/login/email";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvReg = (TextView) findViewById(R.id.tV_reg);
        email = (EditText) findViewById(R.id.lastName);
        password1 = (EditText) findViewById(R.id.email);
        btnLog = (Button) findViewById(R.id.btn_create);
        final  String login = email.getText().toString();
        final String password = password1.getText().toString();
         btnLog.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 LoginApiService mService = RetrofitUtil.getLoginService();
                 Call<LoginResponse> requestMovie = mService.login(new LoginRequest(login, password));
                 requestMovie.enqueue(new Callback<LoginResponse>() {
                     @Override
                     public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                         Log.d(TAG, "onResponse - " + response.body().toString());

                         if (response.isSuccessful() == true) {
                             System.out.println("hahahah");
                         }
                     }

                     @Override
                     public void onFailure(Call<LoginResponse> call, Throwable t) {
                         System.out.println("hahahah1");
                     }
                 });
             }
         });

                                      }
                                  }




   /* public void onClick(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);


    }*/

  /*  public void onClick1(View view) {
        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);


    }*/

