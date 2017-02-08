package com.avevanjagmail.moviesapp.view;

import android.content.Context;

import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;

import java.util.ArrayList;


public interface NewFragmentView {
    void setNewMovies(ArrayList<MovieApi> newMovies);
    void setMoreNewMovies( ArrayList<MovieApi> newMovies);
    void setLocalNewMovies(ArrayList<Movie> localNewMovies);
    Context getContext();
}
