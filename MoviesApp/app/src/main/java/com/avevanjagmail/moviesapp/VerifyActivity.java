package com.avevanjagmail.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by irabokalo on 29.07.2016.
 */
public class VerifyActivity extends AppCompatActivity{
    EditText email, code;
  @Override
  protected void onCreate( Bundle savedInstanceState) {
      super.onCreate( savedInstanceState );
      email = (EditText)(findViewById( R.id.editText ));
      code = (EditText) (findViewById( R.id.editText2 ));



  }



}
