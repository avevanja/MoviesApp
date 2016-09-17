package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductionCountry {
    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }
}
