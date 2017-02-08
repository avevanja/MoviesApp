package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.SerializedName;


public class ActivateData {
    @SerializedName( "u_email" )
    private  String email;

    public String getEmail() {
        return email;
    }
}
