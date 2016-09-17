package com.avevanjagmail.moviesapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    private EditText mEditTextName, mEditTextLastName, mEditTextEmail, mEditTextPassword, mEditTextConfirmPassword;
    private Bitmap thumbnail;
    private Button mButtonRegistration;
    private ProgressDialog mProgressDialog;
    private ImageView mImageViewAvatar;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String mUserChooseTask, mPassedArg, mNewPassedArg;
    private TextView mTextViewRegistered;
    private RegistrationActivityPresenter mRegistrationActivityPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mEditTextName = (EditText) findViewById(R.id.registration_first_name_et);
        mEditTextLastName = (EditText) findViewById(R.id.registration_last_name_et);
        mEditTextEmail = (EditText) findViewById(R.id.registration_email_et);
        mEditTextPassword = (EditText) findViewById(R.id.registration_password_et);
        mImageViewAvatar = (ImageView) findViewById(R.id.registration_photo_iv);
        mButtonRegistration = (Button) (findViewById(R.id.registration_registration_btn));
        mTextViewRegistered = (TextView) (findViewById(R.id.registration_already_tv));
        mEditTextConfirmPassword = (EditText) findViewById(R.id.registration_confirm_password_et);
        mRegistrationActivityPresenter = new RegistrationActivityPresenter();


        mProgressDialog = new ProgressDialog(this);
        mImageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageViewAvatar.setBackgroundResource(0);
                selectImage();
            }
        });
        mTextViewRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        mRegistrationActivityPresenter.setRegistrationActivityView(this);


        mButtonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;
                if (mEditTextName.getText().length() == 0) {
                    mEditTextName.setError("please write name");
                    error = true;
                }
                if (mEditTextLastName.getText().length() == 0) {
                    mEditTextLastName.setError("please write second name");
                    error = true;
                }
                if (mEditTextEmail.getText().length() == 0) {
                    mEditTextEmail.setError("please write email");
                    error = true;
                }
                if (mEditTextPassword.getText().length() == 0) {
                    mEditTextPassword.setError("please write password");
                    error = true;
                }
                if (mEditTextConfirmPassword.getText().length() == 0) {
                    mEditTextConfirmPassword.setError("please confirm password");
                    error = true;
                }
                if (!mEditTextPassword.getText().toString().equals(mEditTextConfirmPassword.getText().toString())) {
                    mEditTextPassword.setError("confirm mEditTextPassword not correct");
                    error = true;
                }
                if (!error) {
                    mPassedArg = mEditTextEmail.getText().toString();
                    mNewPassedArg = mPassedArg.replace(".", "a");
                    if (thumbnail == null) {
                        Toast.makeText(RegistrationActivity.this, "Please add photo", Toast.LENGTH_SHORT).show();
                    } else {
                        mProgressDialog.show();
                        Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
                        Log.d(TAG, "photoUri - " + tempUri.toString());

                        mRegistrationActivityPresenter.uploadPhoto(tempUri, mNewPassedArg);
                        mRegistrationActivityPresenter.doRegisterAndSendVerify(mEditTextName.getText().toString(), mEditTextLastName.getText().toString(), mEditTextEmail.getText().toString(),
                                mEditTextPassword.getText().toString(), mNewPassedArg);
                    }

                }
            }
        });

    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = com.avevanjagmail.moviesapp.utils.Utility.checkPermission(getApplicationContext());
                if (items[item].equals("Take Photo")) {
                    mUserChooseTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    mUserChooseTask = "Choose from Library";
                    if (result)
                        galleryIntent();
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
                    if (mUserChooseTask.equals("Take Photo"))
                        cameraIntent();
                    else if (mUserChooseTask.equals("Choose from Library"))
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
                mProgressDialog.setMessage("Photo is uploading...");
                mProgressDialog.show();
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
        mImageViewAvatar.setImageBitmap(thumbnail);
        mProgressDialog.dismiss();
        this.thumbnail = thumbnail;

    }


}

