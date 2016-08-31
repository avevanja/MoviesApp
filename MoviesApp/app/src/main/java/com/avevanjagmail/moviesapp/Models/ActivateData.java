package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by irabokalo on 01.08.2016.
 */
public class ActivateData {
    @SerializedName( "u_email" )
    private  String email;

    public String getEmail() {
        return email;
    }
}
