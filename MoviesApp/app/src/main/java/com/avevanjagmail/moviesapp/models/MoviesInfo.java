package com.avevanjagmail.moviesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class MoviesInfo {
    @SerializedName("genres")
    @Expose
    private ArrayList<Genre> genres = new ArrayList<Genre>();

    public ArrayList<Genre> getGenres() {
        return genres;
    }
    @SerializedName("overview")
    @Expose
    private String overview;

    public String getOverview() {
        return overview;
    }
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    @SerializedName("production_countries")
    @Expose
    private List<ProductionCountry> productionCountries = new ArrayList<ProductionCountry>();
}
