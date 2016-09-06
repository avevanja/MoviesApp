package com.avevanjagmail.moviesapp.view;

import android.content.Context;

import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;

import java.util.ArrayList;

/**
 * Created by paulg on 06.09.2016.
 */
public interface FavoriteFragmentView {
    void setFavoriteMovies(MovieApi movieApi);
    void setLocalFavoriteMovies(ArrayList<Movie> localFavoriteMovies);
    Context getContext ();
}
