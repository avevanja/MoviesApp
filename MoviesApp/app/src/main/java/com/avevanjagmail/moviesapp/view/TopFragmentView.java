package com.avevanjagmail.moviesapp.view;

import android.content.Context;

import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;

import java.util.ArrayList;

public interface TopFragmentView {
    void setTopMovies(ArrayList<MovieApi> topMovies);
    void setMoreTopMovies( ArrayList<MovieApi> topMovies);
    void setLocalTopMovies(ArrayList<Movie> localTopMovies);
    void stopProgress();
    Context getContext();


}
