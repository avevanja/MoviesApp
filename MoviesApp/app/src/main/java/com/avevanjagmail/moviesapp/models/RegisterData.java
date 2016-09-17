package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


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
