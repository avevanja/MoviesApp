package com.avevanjagmail.moviesapp.view;

import android.content.Context;

import com.avevanjagmail.moviesapp.models.MovieApi;

import java.util.ArrayList;


public interface SearchActivityView {
    void setSearchMoviesList(ArrayList<MovieApi> searchMoviesList);
    Context getContext();
}
