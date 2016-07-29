package com.avevanjagmail.moviesapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by irabokalo on 28.07.2016.
 */
public class LogOutRequest {
    @SerializedName("u_email")
    @Expose
    private String email;
    @SerializedName("u_token")
    @Expose
    private String token;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }
    LogOutRequest(String e, String t){
        email = e;
        token = t;
    }
}
