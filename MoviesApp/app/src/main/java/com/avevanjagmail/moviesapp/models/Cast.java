package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Cast {
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    public String getProfilePath() {
        return profilePath;
    }
    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }
    @SerializedName("order")
    @Expose
    private Integer order;

    public Integer getOrder() {
        return order;
    }
}
