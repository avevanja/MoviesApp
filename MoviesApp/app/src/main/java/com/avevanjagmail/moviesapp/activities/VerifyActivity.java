package com.avevanjagmail.moviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.Interface.LoginApiService;
import com.avevanjagmail.moviesapp.Interface.VerifyActivityView;
import com.avevanjagmail.moviesapp.Models.ActivateRequest;
import com.avevanjagmail.moviesapp.Models.ActivateResponse;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by irabokalo on 29.07.2016.
 */
public class VerifyActivity extends AppCompatActivity implements VerifyActivityView {
    private EditText codeTextEdit;
    private Button activateBtn;
    private VerifyActivityPresentor verifyActivityPresentor = new VerifyActivityPresentor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_verify );
verifyActivityPresentor.setVerifyActivityView(this);

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
                        verifyActivityPresentor.verify(emailT,code);
                    }
                } );

            }
        } );
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}



