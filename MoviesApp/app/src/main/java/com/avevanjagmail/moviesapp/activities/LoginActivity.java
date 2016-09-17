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

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.presenter.LoginActivityPresenter;
import com.avevanjagmail.moviesapp.view.LoginActivityView;
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
        mLoginActivityPresenter.setLoginActivityView(this);
        mLoginBtn = (Button) findViewById(R.id.login_login_btn);


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //логіку перевірки валідності полів бажано перенести в презентер
                //view не повинна знати правил і логіки
                String login = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                boolean error = false;
                if(login.length()==0){
                    mEmailEditText.setError("please write email");
                    error = true;
                }
                if(password.length()==0){
                    mPasswordEditText.setError("please write password");
                    error = true;
                }
                if(!error) {
                    mLoginActivityPresenter.doLogin(login, password);
                }
            }
        });

        mRegisterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //використовуй .starter() для Activity
                //запуск нових Activity винось в презентер
                //передавай звичайний контекст а не Application
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

//SharedPreferences винести в окрему утиліту
        mSharedPreferences = getSharedPreferences("SH", MODE_PRIVATE);
        if (mSharedPreferences.contains(SHARED)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    //звичайний! а не Application!
    //return this;
    //or
    //return LoginActivity.this;
    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onSuccessLoginFaceBook() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class); //має бути в презентері
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccessLogin() {
        Intent intent = new Intent( getApplicationContext(), MainActivity.class );   //і це теж
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

}











