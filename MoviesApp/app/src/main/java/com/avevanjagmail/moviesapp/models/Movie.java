package com.avevanjagmail.moviesapp.models;

import com.orm.SugarRecord;


public class Movie extends SugarRecord {
    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    private String properties;


    private String title;
    private MovieApi movieApi;
    private String backdropPath;
    private String releaseDate;
    private Double voteAverage;


    public Movie(MovieApi movieApi, String properties) {
        this.movieApi = movieApi;
        this.properties = properties;
        title = movieApi.getTitle();
        backdropPath = movieApi.getBackdropPath();
        releaseDate = movieApi.getReleaseDate();
        voteAverage = movieApi.getVoteAverage();


    }

    public MovieApi getMovieApi() {
        return movieApi;

    }

    public Movie() {
    }


}