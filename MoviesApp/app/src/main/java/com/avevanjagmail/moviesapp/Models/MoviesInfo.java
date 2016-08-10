package com.avevanjagmail.moviesapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulg on 08.08.2016.
 */
public class MoviesInfo {
    @SerializedName("genres")
    @Expose
    private List<Genre> genres = new ArrayList<Genre>();

    public List<Genre> getGenres() {
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
