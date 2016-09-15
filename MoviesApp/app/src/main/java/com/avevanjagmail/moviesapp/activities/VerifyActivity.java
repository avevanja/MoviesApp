package com.avevanjagmail.moviesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.Interface.VerifyActivityView;
import com.avevanjagmail.moviesapp.R;


public class VerifyActivity extends AppCompatActivity implements VerifyActivityView {
    private EditText mCodeTextEdit;
    private Button mActivateBtn;
    private VerifyActivityPresenter verifyActivityPresenter = new VerifyActivityPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        verifyActivityPresenter.setVerifyActivityView(this);

        mActivateBtn = (Button) findViewById(R.id.activate_btn);
        mCodeTextEdit = (EditText) findViewById(R.id.editText);


        mActivateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String code = mCodeTextEdit.getText().toString();
                String emailT = getIntent().getStringExtra("email");
                verifyActivityPresenter.verify(emailT, code);

            }
        });
    }


    @Override
    public void succeededVerify() {
        Toast toast = Toast.makeText(getApplicationContext(), "Congratulations you activated your account!", Toast.LENGTH_LONG);
        toast.show();
        Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(myIntent);
    }
}



