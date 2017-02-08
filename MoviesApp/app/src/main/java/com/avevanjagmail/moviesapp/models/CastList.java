package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class CastList {
    @SerializedName("cast")
    @Expose
    private ArrayList<Cast> cast = new ArrayList<Cast>();

    public ArrayList<Cast> getCast() {
        return cast;
    }
}
