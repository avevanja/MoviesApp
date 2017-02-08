package com.avevanjagmail.moviesapp.view;

import android.content.Context;
import android.graphics.Bitmap;


public interface RegistrationActivityView {
    Context getContext();
    void onSuccessVerify();
    void failRegistration(String error);
    void failVerify(String error);
    void setImage(Bitmap thumbnail);
    void setErrorName(String error);
    void setErrorLastName(String error);
    void setErrorEmail(String error);
    void setErrorPassword(String error);
    void setErrorConfirmPassword(String error);
    void setErrorConfirmPasswordEqualsPassword(String error);




}
