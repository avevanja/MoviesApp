package com.avevanjagmail.moviesapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by paulg on 10.08.2016.
 */
public class ProductionCountry {
    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }
}
