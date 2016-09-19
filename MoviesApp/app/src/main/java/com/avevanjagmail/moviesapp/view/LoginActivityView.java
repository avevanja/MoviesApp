package com.avevanjagmail.moviesapp.view;

import android.content.Context;


public interface LoginActivityView {
    Context getContext();

    void onSuccessLoginFaceBook();

    void onSuccessLogin();

    void errorLogin(String error);

    void errorPassword(String error);

}
