package com.avevanjagmail.moviesapp.view;

import android.content.Context;

import com.avevanjagmail.moviesapp.models.Cast;
import com.avevanjagmail.moviesapp.models.MoviesInfo;

import java.util.ArrayList;


public interface InformActivityView {
    void setCastList(ArrayList<Cast> castList);
    void setMovieInfo(MoviesInfo movieInfo);
    Context getContext();
}
