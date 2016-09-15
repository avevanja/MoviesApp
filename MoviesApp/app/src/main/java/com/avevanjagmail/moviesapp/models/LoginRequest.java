package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by irabokalo on 27.07.2016.
 */
public class LoginRequest {
    @SerializedName("u_email")
    @Expose
    private String email;

    @SerializedName("u_password")
    @Expose
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
