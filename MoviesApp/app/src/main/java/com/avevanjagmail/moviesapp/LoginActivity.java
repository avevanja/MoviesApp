package com.avevanjagmail.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    TextView tvReg;
    Button btnLog;
    EditText logText, passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvReg = (TextView) findViewById(R.id.tV_reg);
        logText = (EditText) findViewById(R.id.editText);
        passwordText = (EditText) findViewById(R.id.editText2);
        btnLog = (Button) findViewById(R.id.btn_log);
      // logText.setOnClickListener();

    }


   public void onClick(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);


        }
    public void onClick1(View view) {
        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);


    }

    }
