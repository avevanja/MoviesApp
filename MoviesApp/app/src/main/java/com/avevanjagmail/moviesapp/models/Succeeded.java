package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.Expose;

/**
 * Created by irabokalo on 27.07.2016.
 */
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
