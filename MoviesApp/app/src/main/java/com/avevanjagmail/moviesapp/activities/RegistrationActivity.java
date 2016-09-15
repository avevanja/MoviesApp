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
import android.os.Environment;
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

import com.avevanjagmail.moviesapp.Interface.RegistrationActivityView;
import com.avevanjagmail.moviesapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by John on 08.07.2016.
 */
public class RegistrationActivity extends AppCompatActivity implements RegistrationActivityView {
    final String TAG = RegistrationActivity.class.getSimpleName();
    private EditText fName, lName, email, password, confirmPassword;
    private Bitmap thumbnail;
    private Button btn_create;
    private SharedPreferences prefForUsername, prefForEmail;
    private ProgressDialog progressDialog;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReferenceFromUrl("gs://movies-app-fda81.appspot.com");
    private ImageView ivImage;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask, passedArg, newPassedArg;
    private TextView registered;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserId = mRootRef.child("Users");
    private RegistrationActivityPresenter registrationActivityPresenter = new RegistrationActivityPresenter();

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
        registrationActivityPresenter.setRegistrationActivityView(this);


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
                    progressDialog.show();
                    passedArg = email.getText().toString();
                    newPassedArg = passedArg.replace(".", "a");
                    Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
                    Log.d(TAG, "photoUri - " + tempUri.toString());
                    registrationActivityPresenter.doRegisterAndSendVerify(fName.getText().toString(), lName.getText().toString(), email.getText().toString(),
                            password.getText().toString());
                    registrationActivityPresenter.uploadPhoto(tempUri, newPassedArg);

//                    StorageReference mountainImagesRef = storageRef.child("images/" + tempUri.getLastPathSegment());
////                    progressDialog.dismiss();
////                    progressDialog.show();
//                    UploadTask uploadTask = mountainImagesRef.putFile(tempUri);
//                    uploadTask.addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            e.printStackTrace();
//                            e.printStackTrace();
//                        }
//                    })
//                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                                    String Uri = downloadUrl.toString();
//                                    mUserId.child(newPassedArg).child("Photos").setValue(Uri);
//                                    Log.d(TAG, "downloaded image - " + downloadUrl.toString());
//                                    progressDialog.dismiss();
//
//                                }
//                            });
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
                    //code for deny
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

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {


        if (data != null) {
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ivImage.setImageBitmap(thumbnail);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA) {
                progressDialog.setMessage("Photo is uploading...");
                progressDialog.show();
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);


        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ivImage.setImageBitmap(thumbnail);
        progressDialog.dismiss();


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
    }

    @Override
    public void failRegistration(String error) {
        Toast.makeText(RegistrationActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failVerify(String error) {
        Toast.makeText(RegistrationActivity.this, error, Toast.LENGTH_SHORT).show();
    }



}

