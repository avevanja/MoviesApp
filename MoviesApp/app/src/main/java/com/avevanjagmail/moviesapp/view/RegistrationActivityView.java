package com.avevanjagmail.moviesapp.view;

import android.content.Context;
import android.graphics.Bitmap;


public interface RegistrationActivityView {
    Context getContext();
    void onSuccessVerify(String email);
    void failRegistration(String error);
    void failVerify(String error);
    void setImage(Bitmap thumbnail);



}
