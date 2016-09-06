package com.avevanjagmail.moviesapp.view;

import com.avevanjagmail.moviesapp.models.Cast;
import com.avevanjagmail.moviesapp.models.MoviesInfo;

import java.util.ArrayList;

/**
 * Created by paulg on 06.09.2016.
 */
public interface InformActivityView {
    void setCastList(ArrayList<Cast> castList);

    void setMovieInfo(MoviesInfo movieInfo);
}
