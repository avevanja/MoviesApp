package com.avevanjagmail.moviesapp.Interface;

import android.content.Context;

/**
 * Created by irabokalo on 10.09.2016.
 */
public interface RegistrationActivityView {
    Context getContext();
    void onSuccessVerify(String email);
    void failRegistration(String error);
    void failVerify(String error);



}
