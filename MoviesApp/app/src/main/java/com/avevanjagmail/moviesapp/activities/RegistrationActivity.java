package com.avevanjagmail.moviesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avevanjagmail.moviesapp.Interface.LoginApiService;
import com.avevanjagmail.moviesapp.Models.RegisterRequest;
import com.avevanjagmail.moviesapp.Models.RegisterResponse;
import com.avevanjagmail.moviesapp.Models.VerifyRequest;
import com.avevanjagmail.moviesapp.Models.VerifyResponse;
import com.avevanjagmail.moviesapp.R;
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
        setContentView( R.layout.activity_registration);
       fName = (EditText) findViewById(R.id.firstName);
        lName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btn_create = (Button)(findViewById(R.id.btn_create));
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailText = email.getText().toString();


                LoginApiService mService = RetrofitUtil.getLoginService();

                Call<RegisterResponse> requestMovie = mService.register(new RegisterRequest(lName.getText().toString(),
                        fName.getText().toString(),email.getText().toString(),password.getText().toString(),"0"));
                requestMovie.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        Log.d(TAG, "onResponse - " + response.body().toString());
                        System.out.println(response.body().getData().getEmail());
                        System.out.println(response.body().getData().getVerified());
                        System.out.println(response.body().getSucceeded().message);
                        System.out.println(response.body().getSucceeded().success);


                        Intent myintent=new Intent(getApplicationContext(), VerifyActivity.class).putExtra("<email>",emailText);
                        startActivity(myintent);


                       /* if (response.body().getSucceeded().success == true) {
                            Intent intent = new Intent( getApplicationContext(), VerifyActivity.class );
                            startActivity( intent );
                        }

                        if (response.body().getSucceeded().success == false)
                           {
                               Toast toast = Toast.makeText(getApplicationContext(), response.body().getSucceeded().message,Toast.LENGTH_LONG);
                               toast.show();
                           }*/
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        System.out.println("hahahah1");
                    }
                });
                Call<VerifyResponse> requestInfo = mService.verify( new VerifyRequest( emailText ) );
                requestInfo.enqueue( new Callback<VerifyResponse>() {


                    @Override
                    public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                        System.out.println( response.body().getSucceeded().message);
                        System.out.println( response.body().getSucceeded().success);
                        System.out.println(response.body().getData() );
                    }

                    @Override
                    public void onFailure(Call<VerifyResponse> call, Throwable t) {
                        System.out.println( t.getCause() );
                        t.printStackTrace();
                        System.out.println("Error");
                    }
                } );



        //LoginApiService mService = RetrofitUtil.getLoginService();

            }
        });

    }
}

