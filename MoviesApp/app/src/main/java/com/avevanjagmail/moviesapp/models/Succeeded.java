package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.Expose;


public class Succeeded {
    @Expose
    public boolean success;
    @Expose
    public String message;

    @Override
    public String toString() {
        return "Succeeded{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
