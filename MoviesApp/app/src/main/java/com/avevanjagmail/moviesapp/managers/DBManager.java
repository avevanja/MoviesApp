package com.avevanjagmail.moviesapp.managers;

import com.avevanjagmail.moviesapp.models.Movie;

import java.util.List;


public class DBManager {

    public static void save(List<Movie> movies) {
        for (Movie movie : movies) {
            save(movie);
        }
    }

    public static void save(Movie movie) {
        movie.save();
    }
}
