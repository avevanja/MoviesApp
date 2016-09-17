package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.SerializedName;


public class ActivateRequest {
    @SerializedName( "v_code" )
    private  String code;
    @SerializedName( "u_email" )
    private String email;
   public ActivateRequest(String c, String e)
    {
        email =e;
        code = c;
    }
    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }
}
