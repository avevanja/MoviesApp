package com.avevanjagmail.moviesapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by irabokalo on 28.07.2016.
 */
public class RegisterRequest {
    @SerializedName("f_name")
    @Expose
    private String mFirstName;
    @SerializedName("l_name")
    @Expose
    private  String mLastName;
    @SerializedName("u_email")
    @Expose
    private String mEmail;
    private String mRole;
    public RegisterRequest(String f_name, String l_name, String u_email, String role)
    {
        mFirstName = f_name;
        mLastName = l_name;
        mEmail = u_email;
        mRole = role;
    }
}
