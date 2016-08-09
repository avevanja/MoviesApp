package com.avevanjagmail.moviesapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulg on 09.08.2016.
 */
public class CastList {
    @SerializedName("cast")
    @Expose
    private List<Cast> cast = new ArrayList<Cast>();

    public List<Cast> getCast() {
        return cast;
    }
}
