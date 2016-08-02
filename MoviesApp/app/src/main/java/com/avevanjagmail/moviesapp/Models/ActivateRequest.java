package com.avevanjagmail.moviesapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by irabokalo on 01.08.2016.
 */
public class ActivateRequest {
    @SerializedName( "v_code" )
    private  int code;
    @SerializedName( "u_email" )
    private String email;
   public ActivateRequest(int c, String e)
    {
        email =e;
        code = c;
    }
    public String getEmail() {
        return email;
    }

    public int getCode() {
        return code;
    }
}
