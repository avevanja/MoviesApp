package com.avevanjagmail.moviesapp.view;

import android.content.Context;

import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;

import java.util.ArrayList;


public interface FavoriteFragmentView {
    void setFavoriteMovies(MovieApi movieApi);

    void setLocalFavoriteMovies(ArrayList<Movie> localFavoriteMovies);

    Context getContext();
}
