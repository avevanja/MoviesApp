package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.SerializedName;


public class MovieApi {
    @SerializedName("backdrop_path")
    private String backdropPath;


    @SerializedName("title")
    private String title;


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

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    @Override
    public String toString() {
        return "MovieApi{" +
                "backdropPath='" + backdropPath + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", id='" + id + '\'' +
                ", voteAverage=" + voteAverage +
                '}';
    }
}
