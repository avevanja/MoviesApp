package com.avevanjagmail.moviesapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.Interface.LoginApiService;
import com.avevanjagmail.moviesapp.Models.LoginRequest;
import com.avevanjagmail.moviesapp.Models.LoginResponse;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sPref;
    SharedPreferences sPref1;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private TextView tvReg;
    private Button btnLog;
    private EditText email, password1;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserId = mRootRef.child("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        final String TAG = LoginActivity.class.getSimpleName();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               final Profile profile = Profile.getCurrentProfile();
                sPref = getSharedPreferences("SH", MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("saved_text", profile.getId().toString());
                ed.commit();


              final  String imageUrl = String.valueOf(profile.getProfilePictureUri(717, 400));

mRootRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.child("Users").child(profile.getId()).hasChild("need photo") == true)
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (dataSnapshot.child("Users").child(profile.getId()).hasChild("need photo")== false)
        {
            mUserId.child(profile.getId()).child("Photos").setValue(imageUrl);
            mUserId.child(profile.getId()).child("need photo").setValue("no");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});

            }

            @Override
            public void onCancel() {
                Log.d("On cancel", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("On error", exception.getMessage().toString());
            }
        });

        tvReg = (TextView) findViewById(R.id.tV_reg);
        email = (EditText) findViewById(R.id.lastName);
        password1 = (EditText) findViewById(R.id.email)
        ;
        btnLog = (Button) findViewById(R.id.btn_create);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginApiService mService = RetrofitUtil.getLoginService();
                final String login = email.getText().toString();
                String password = password1.getText().toString();
                final Call<LoginResponse> requestMovie = mService.login(new LoginRequest(login, password));
                requestMovie.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        Log.d(TAG, "onResponse - " + response.body().toString());

                        if (response.body().getSucceeded().success==true)
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "You have logged successfully",Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                            startActivity(intent);
                            finish();
                            sPref = getSharedPreferences("SH",MODE_PRIVATE);
                            SharedPreferences.Editor ed = sPref.edit();
                            ed.putString("saved_text", response.body().getData().getEmail());
                            ed.commit();
                            Log.d("sh", sPref.getString("saved_text", ""));
                            sPref1 = getSharedPreferences("SH1",MODE_PRIVATE);
                            SharedPreferences.Editor ed1 = sPref1.edit();
                            ed1.putString("saved_text1", response.body().getData().accessToken);
                            ed1.commit();
                            Log.d("sh1", sPref1.getString("saved_text1", ""));

                        }
                        else if (response.body().getSucceeded().success==false)
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Password or Login is incorrect. Try again.",Toast.LENGTH_LONG);
                            toast.show();

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast toast = Toast.makeText(getApplicationContext(), "You failed! Try to check your internet connection", Toast.LENGTH_LONG);
                        toast.show();

                    }
                });
            }
        });
        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });
        sPref = getSharedPreferences("SH", MODE_PRIVATE);
        if(sPref.contains("saved_text")==true){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}











