package com.avevanjagmail.moviesapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.Interface.LoginApiService;
import com.avevanjagmail.moviesapp.Models.ActivateRequest;
import com.avevanjagmail.moviesapp.Models.ActivateResponse;
import com.avevanjagmail.moviesapp.Models.VerifyRequest;
import com.avevanjagmail.moviesapp.Models.VerifyResponse;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by irabokalo on 29.07.2016.
 */
public class VerifyActivity extends AppCompatActivity {
    EditText codeTextEdit;

    Button activateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_verify );


          activateBtn = (Button) findViewById( R.id.activate_btn );
            codeTextEdit = (EditText) findViewById( R.id.editText );


        activateBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                activateBtn.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String code  = codeTextEdit.getText().toString();
                        String emailT= getIntent().getStringExtra("email");
                        LoginApiService mService = RetrofitUtil.getLoginService();
                        Call<ActivateResponse> requestInfo = mService.activate( new ActivateRequest( code, emailT ) );
                        requestInfo.enqueue( new Callback<ActivateResponse>() {
                            @Override
                            public void onResponse(Call<ActivateResponse> call, Response<ActivateResponse> response) {
                                System.out.println( response.body().getSucceeded().message );

                                if (response.body().getSucceeded().success == true)
                                {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Congratulations you activated your account!",Toast.LENGTH_LONG);
                                    toast.show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ActivateResponse> call, Throwable t) {
                                System.out.println( "Errror" );
                            }
                        } );
                    }
                } );

            }
        } );
    }
}



