package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by paulg on 09.08.2016.
 */
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
