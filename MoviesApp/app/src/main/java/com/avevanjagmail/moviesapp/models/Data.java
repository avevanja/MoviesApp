package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {
    @Expose
    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("u_email")
    @Expose
    public  String email;
     public String getEmail() {
        return email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        return "Data{" +
                "accessToken='" + accessToken + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
