package com.avevanjagmail.moviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.presenter.VerifyActivityPresenter;
import com.avevanjagmail.moviesapp.view.VerifyActivityView;


public class VerifyActivity extends AppCompatActivity implements VerifyActivityView {
    private EditText mCodeTextEdit;
    private Button mActivateBtn;
    private VerifyActivityPresenter verifyActivityPresenter = new VerifyActivityPresenter();
    public static void start(Context context, String email) {
        Intent starter = new Intent(context, VerifyActivity.class);
        starter.putExtra("email", email);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        verifyActivityPresenter.setVerifyActivityView(this);
        mActivateBtn = (Button) findViewById(R.id.activate_btn);
        mCodeTextEdit = (EditText) findViewById(R.id.activate_code_et);


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
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        verifyActivityPresenter.onDestroy();
    }
}



