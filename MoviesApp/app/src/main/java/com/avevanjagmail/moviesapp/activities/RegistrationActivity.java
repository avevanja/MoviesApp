package com.avevanjagmail.moviesapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.presenter.RegistrationActivityPresenter;
import com.avevanjagmail.moviesapp.view.RegistrationActivityView;
import com.avevanjagmail.moviesapp.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by John on 08.07.2016.
 */
public class RegistrationActivity extends AppCompatActivity implements RegistrationActivityView {
    final String TAG = RegistrationActivity.class.getSimpleName();
    private EditText fName, lName, email, password, confirmPassword;
    private Bitmap thumbnail;
    private Button btn_create;
    private ProgressDialog progressDialog;
    private ImageView ivImage;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask, passedArg, newPassedArg;
    private TextView registered;
    private RegistrationActivityPresenter mRegistrationActivityPresenter;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        fName = (EditText) findViewById(R.id.firstName);
        lName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        ivImage = (ImageView) findViewById(R.id.cast_foto3);
        btn_create = (Button) (findViewById(R.id.btn_create));
        registered = (TextView) (findViewById(R.id.registered));
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        mRegistrationActivityPresenter = new RegistrationActivityPresenter();


        progressDialog = new ProgressDialog(this);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivImage.setBackgroundResource(0);
                selectImage();
            }
        });
        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        mRegistrationActivityPresenter.setRegistrationActivityView(this);


        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;
                if (fName.getText().length() == 0) {
                    fName.setError("please write name");
                    error = true;
                }
                if (lName.getText().length() == 0) {
                    lName.setError("please write second name");
                    error = true;
                }
                if (email.getText().length() == 0) {
                    email.setError("please write email");
                    error = true;
                }
                if (password.getText().length() == 0) {
                    password.setError("please write password");
                    error = true;
                }
                if (confirmPassword.getText().length() == 0) {
                    confirmPassword.setError("please confirm password");
                    error = true;
                }
                if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    password.setError("confirm password not correct");
                    error = true;
                }
                if (!error) {
                    passedArg = email.getText().toString();
                    newPassedArg = passedArg.replace(".", "a");
                    if(thumbnail==null){
                        Toast.makeText(RegistrationActivity.this, "Please add photo", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressDialog.show();
                        Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
                        Log.d(TAG, "photoUri - " + tempUri.toString());

                        mRegistrationActivityPresenter.uploadPhoto(tempUri, newPassedArg);
                        mRegistrationActivityPresenter.doRegisterAndSendVerify(fName.getText().toString(), lName.getText().toString(), email.getText().toString(),
                                password.getText().toString(), newPassedArg);
                    }

                }
            }
        });

    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = com.avevanjagmail.moviesapp.utils.Utility.checkPermission(getApplicationContext());
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case com.avevanjagmail.moviesapp.utils.Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {

                }
                break;
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                mRegistrationActivityPresenter.onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA) {
                progressDialog.setMessage("Photo is uploading...");
                progressDialog.show();
                mRegistrationActivityPresenter.onCaptureImageResult(data);
            }
        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onSuccessVerify(String email) {
        Intent myIntent = new Intent(getApplicationContext(), VerifyActivity.class).putExtra("email", email);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(myIntent);
        finish();
    }

    @Override
    public void failRegistration(String error) {
        Toast.makeText(RegistrationActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failVerify(String error) {
        Toast.makeText(RegistrationActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setImage(Bitmap thumbnail) {
        ivImage.setImageBitmap(thumbnail);
        progressDialog.dismiss();
        this.thumbnail = thumbnail;

    }


}

