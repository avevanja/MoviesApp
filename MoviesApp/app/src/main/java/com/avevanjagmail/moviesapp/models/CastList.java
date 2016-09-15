package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by paulg on 09.08.2016.
 */
public class CastList {
    @SerializedName("cast")
    @Expose
    private ArrayList<Cast> cast = new ArrayList<Cast>();

    public ArrayList<Cast> getCast() {
        return cast;
    }
}
