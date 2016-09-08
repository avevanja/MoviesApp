package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by John on 15.07.2016.
 */
public class ListMovie {




    @SerializedName("results")
    @Expose
    private ArrayList<MovieApi> results = new ArrayList<MovieApi>();

    public ArrayList<MovieApi> getResults() {
        return results;
    }
}
