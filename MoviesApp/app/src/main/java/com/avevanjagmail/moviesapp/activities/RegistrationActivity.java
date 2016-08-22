package com.avevanjagmail.moviesapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.Interface.LoginApiService;
import com.avevanjagmail.moviesapp.Models.RegisterRequest;
import com.avevanjagmail.moviesapp.Models.RegisterResponse;
import com.avevanjagmail.moviesapp.Models.VerifyRequest;
import com.avevanjagmail.moviesapp.Models.VerifyResponse;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by John on 08.07.2016.
 */
public class RegistrationActivity extends AppCompatActivity {
    final String TAG = RegistrationActivity.class.getSimpleName();
    EditText fName, lName, email, password;
    Button btn_create;
    private ProgressDialog progressDialog;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://movies-app-fda81.appspot.com");
    private ImageView ivImage;
    private String url1;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    SharedPreferences sPref;
    DatabaseReference mUserId = mRootRef.child("Users");
    DatabaseReference mMovie = mUserId.child("Movies");
    DatabaseReference mPhoto = mUserId.child("Photos");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        fName = (EditText) findViewById(R.id.firstName);
        lName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        ivImage = (ImageView) findViewById(R.id.cast_foto3);
        progressDialog = new ProgressDialog(this);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btn_create = (Button) (findViewById(R.id.btn_create));
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailText = email.getText().toString();

                LoginApiService mService = RetrofitUtil.getLoginService();

                Call<RegisterResponse> requestMovie = mService.register(new RegisterRequest(lName.getText().toString(),
                        fName.getText().toString(), email.getText().toString(), password.getText().toString(), "0"));
                requestMovie.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        Log.d(TAG, "onResponse - " + response.body().toString());

                        if (response.body().getSucceeded().success == true) {
                            Intent myintent = new Intent(getApplicationContext(), VerifyActivity.class).putExtra("<email>", emailText);
                            startActivity(myintent);
                        }

                        if (response.body().getSucceeded().success == false) {
                            Toast toast = Toast.makeText(getApplicationContext(), response.body().getSucceeded().message, Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast toast = Toast.makeText(getApplicationContext(),t.getCause().toString(),Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
                Call<VerifyResponse> requestInfo = mService.verify(new VerifyRequest(emailText));
                requestInfo.enqueue(new Callback<VerifyResponse>() {


                    @Override
                    public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                        if (response.body().getSucceeded().success==true)
                        {
                            Toast toast = Toast.makeText(getApplicationContext(),"You have activated successfully!",Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<VerifyResponse> call, Throwable t) {
                        Toast toast = Toast.makeText(getApplicationContext(),t.getCause().toString(),Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
        });

    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Add Photo!");
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
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //TODO: set the photo on Firebase
        ivImage.setImageBitmap(bm);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                progressDialog.setMessage("Photo is uploading...");
            progressDialog.show();
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        sPref = getSharedPreferences("SH", MODE_PRIVATE);

        String passedArg1 = sPref.getString("saved_text", "");
        final String passedArg = passedArg1.replace(".", "a");
         Log.d("blaa", passedArg);


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
        Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
        Log.d(TAG, "photoUri - " + tempUri.toString());

        StorageReference mountainImagesRef = storageRef.child("images/" + tempUri.getLastPathSegment());
        UploadTask uploadTask = mountainImagesRef.putFile(tempUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        String Uri = downloadUrl.toString();
                        mUserId.child(passedArg).child("Photos").setValue(Uri);

                        Log.d(TAG, "downloaded image - " + downloadUrl.toString());

                        progressDialog.dismiss();
                    }
                });


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

}

