package com.avevanjagmail.moviesapp.Models;

import com.google.gson.annotations.Expose;

/**
 * Created by irabokalo on 28.07.2016.
 */
public class RegisterResponse {
    @Expose
  Succeeded succeeded;
    @Expose
    RegisterData data;
}
