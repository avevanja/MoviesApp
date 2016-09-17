package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.Expose;


public class RegisterResponse {
    @Expose
  Succeeded succeeded;
    @Expose
    RegisterData data;

    public Succeeded getSucceeded() {
        return succeeded;
    }

    public RegisterData getData() {
        return data;
    }
}
