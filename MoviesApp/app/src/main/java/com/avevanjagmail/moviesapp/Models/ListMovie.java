package com.avevanjagmail.moviesapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 15.07.2016.
 */
public class ListMovie {




    @SerializedName("results")
    @Expose
    private List<Movie> results = new ArrayList<Movie>();

    public List<Movie> getResults() {
        return results;
    }
}
