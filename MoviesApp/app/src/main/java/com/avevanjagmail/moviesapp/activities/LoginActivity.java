package com.avevanjagmail.moviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.presenter.LoginActivityPresenter;
import com.avevanjagmail.moviesapp.view.LoginActivityView;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity implements LoginActivityView {

    private LoginActivityPresenter mLoginActivityPresenter = new LoginActivityPresenter();
    private static final String TAG = LoginActivity.class.getSimpleName();
    private TextView mRegisterView;
    private Button mLoginBtn;
    private EditText mEmailEditText, mPasswordEditText;
    private LoginButton mFaceBookLoginButton;
    private CallbackManager mCallbackManager;
    private TextInputLayout mTextInputLayoutLogin, mTextInputLayoutPassword;
    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFaceBookLoginButton = (LoginButton) findViewById(R.id.login_facebook_login_btn);
        mCallbackManager = CallbackManager.Factory.create();
        mFaceBookLoginButton.registerCallback(mCallbackManager, mLoginActivityPresenter.facebookLogin());
        mRegisterView = (TextView) findViewById(R.id.login_register_tv);
        mEmailEditText = (EditText) findViewById(R.id.login_email_et);
        mPasswordEditText = (EditText) findViewById(R.id.login_password_et);
        mTextInputLayoutLogin = (TextInputLayout) findViewById(R.id.login_email_til);
        mTextInputLayoutPassword = (TextInputLayout) findViewById(R.id.login_password_til);
        mLoginActivityPresenter.setLoginActivityView(this);
        mLoginBtn = (Button) findViewById(R.id.login_login_btn);


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                mLoginActivityPresenter.doLogin(login, password);

            }
        });

        mRegisterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginActivityPresenter.startRegistrationActivity();
            }
        });
        mLoginActivityPresenter.checkLogin();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSuccessLoginFaceBook() {
        finish();
    }

    @Override
    public void onSuccessLogin() {
        finish();
    }

    @Override
    public void errorLogin(String error) {
        mTextInputLayoutLogin.setError(error);
    }

    @Override
    public void errorPassword(String error) {
        mTextInputLayoutPassword.setError(error);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

}











