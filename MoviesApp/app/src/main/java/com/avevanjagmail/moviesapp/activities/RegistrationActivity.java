package com.avevanjagmail.moviesapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.presenter.RegistrationActivityPresenter;
import com.avevanjagmail.moviesapp.view.RegistrationActivityView;

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
    private String mUserChooseTask;
    private TextView mTextViewRegistered;
    private TextInputLayout mTextInputLayoutName, mTextInputLayoutLastName, mTextInputLayoutEmail, mTextInputLayoutPassword, mTextInputLayoutConfirmPassword;
    private RegistrationActivityPresenter mRegistrationActivityPresenter;
    public static void start(Context context) {
        Intent starter = new Intent(context, RegistrationActivity.class);
        context.startActivity(starter);
    }


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
        mTextInputLayoutName = (TextInputLayout) findViewById(R.id.registration_name_til);
        mTextInputLayoutLastName = (TextInputLayout) findViewById(R.id.registration_last_name_til);
        mTextInputLayoutEmail = (TextInputLayout) findViewById(R.id.registration_email_til);
        mTextInputLayoutPassword = (TextInputLayout) findViewById(R.id.registration_password_til);
        mTextInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.registration_confirm_password_til);


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
                finish();
            }
        });
        mRegistrationActivityPresenter.setRegistrationActivityView(this);


        mButtonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegistrationActivityPresenter.chekRegisterFieldAndDoRegistration(mEditTextName.getText().toString(), mEditTextLastName.getText().toString(),mEditTextEmail.getText().toString(), mEditTextPassword.getText().toString(),mEditTextConfirmPassword.getText().toString(), thumbnail);

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



    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSuccessVerify() {
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

    @Override
    public void setErrorName(String error) {
        mTextInputLayoutName.setError(error);
    }

    @Override
    public void setErrorLastName(String error) {
        mTextInputLayoutLastName.setError(error);

    }

    @Override
    public void setErrorEmail(String error) {
        mTextInputLayoutEmail.setError(error);

    }

    @Override
    public void setErrorPassword(String error) {
        mTextInputLayoutPassword.setError(error);

    }

    @Override
    public void setErrorConfirmPassword(String error) {
        mTextInputLayoutPassword.setError(error);

    }

    @Override
    public void setErrorConfirmPasswordEqualsPassword(String error) {
        mTextInputLayoutConfirmPassword.setError(error);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegistrationActivityPresenter.onDestroy();
    }
}

