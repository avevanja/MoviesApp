package com.avevanjagmail.moviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.presenter.LoginActivityPresenter;
import com.avevanjagmail.moviesapp.view.LoginActivityView;
import com.avevanjagmail.moviesapp.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity implements LoginActivityView {

    private LoginActivityPresenter mLoginActivityPresenter = new LoginActivityPresenter();
    private SharedPreferences mSharedPreferences;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private TextView mRegisterView;
    private Button mLoginBtn;
    private EditText mEmailEditText, mPasswordEditText;
    private LoginButton mFaceBookLoginButton;
    private CallbackManager mCallbackManager;
    private static final String SHARED = "emailOrId";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFaceBookLoginButton = (LoginButton) findViewById(R.id.login_button);
        mCallbackManager = CallbackManager.Factory.create();
        mFaceBookLoginButton.registerCallback(mCallbackManager, mLoginActivityPresenter.facebookLogin());
        mRegisterView = (TextView) findViewById(R.id.register_text_view);
        mEmailEditText = (EditText) findViewById(R.id.lastName);
        mPasswordEditText = (EditText) findViewById(R.id.email);
        mLoginActivityPresenter.setLoginActivityView(this);
        mLoginBtn = (Button) findViewById(R.id.btn_create);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String login = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                if ((mEmailEditText.getText().toString().equals("") ) && (mPasswordEditText.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(), "Input login and password, please", Toast.LENGTH_SHORT).show();
                } else {
                    if (mEmailEditText.getText().toString() == "") {
                        Toast.makeText(getApplicationContext(), "Input login, please", Toast.LENGTH_SHORT).show();
                    }
                    if (mPasswordEditText.getText().toString() == "") {
                        Toast.makeText(getApplicationContext(), "Input password,please", Toast.LENGTH_LONG).show();
                    } else {
                        mLoginActivityPresenter.doLogin(login, password);

                    }
                }
            }
        });
        mRegisterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        mSharedPreferences = getSharedPreferences("SH", MODE_PRIVATE);
        if (mSharedPreferences.contains(SHARED)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onSuccessLoginFaceBook() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccessLogin() {
        Intent intent = new Intent( getApplicationContext(), MainActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}











