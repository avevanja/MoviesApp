package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by irabokalo on 28.07.2016.
 */
public class LogOutRequest {
    @SerializedName("u_email")
    @Expose
    private String email;
    @SerializedName("a_token")
    @Expose
    private String token;
   public LogOutRequest(String e, String t )
   {
       email = e;
       token = t;
   }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
