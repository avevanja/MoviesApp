package com.avevanjagmail.moviesapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by irabokalo on 17.08.2016.
 */
public class LogoutResponse {
    @SerializedName("u_email")
    @Expose
    private String email;

    @SerializedName("a_token")
    @Expose
    private String token;

}
