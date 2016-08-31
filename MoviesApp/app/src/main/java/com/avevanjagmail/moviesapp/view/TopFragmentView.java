package com.avevanjagmail.moviesapp.view;

import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;

import java.util.ArrayList;

/**
 * Created by paulg on 31.08.2016.
 */
public interface TopFragmentView {
    void setTopMovies(ArrayList<MovieApi> topMovies);
    void setMoreTopMovies( ArrayList<MovieApi> topMovies);
    void setLocalTopMovies(ArrayList<Movie> localTopMovies);

}
