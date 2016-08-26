package com.avevanjagmail.moviesapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by paulg on 26.08.2016.
 */
public class MovieApi {
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    public String getBackdropPath() {
        return backdropPath;
    }

    @SerializedName("title")
    @Expose
    private String title;
    public String getTitle() {
        return title;
    }

    @SerializedName("release_date")

    private String releaseDate;
    @SerializedName("id")

    private String id;
    @SerializedName("vote_average")

    private Double voteAverage;



    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getId() {
        return id;
    }
}
