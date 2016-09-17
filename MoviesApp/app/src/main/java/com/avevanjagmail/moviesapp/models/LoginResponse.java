package com.avevanjagmail.moviesapp.models;


public class LoginResponse {
 private Succeeded succeeded;
 private Data data;

    public Data getData() {
        return data;
    }

    public Succeeded getSucceeded() {
        return succeeded;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "succeeded=" + succeeded +
                ", data=" + data +
                '}';
    }
}
