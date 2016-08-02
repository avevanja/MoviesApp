package com.avevanjagmail.moviesapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by irabokalo on 28.07.2016.
 */
public class RegisterData {
    @SerializedName("u_email")
    @Expose
    private String email;
    private String verified;

    public String getEmail() {
        return email;
    }

    public String getVerified() {
        return verified;
    }
}
