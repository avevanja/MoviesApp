package com.avevanjagmail.moviesapp.managers;

import com.avevanjagmail.moviesapp.models.Movie;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;


public class DBManager {



    public static void save(Movie movie) {
        movie.save();
    }
    public static ArrayList<Movie> getLocalListMovie (String properties){
       return (ArrayList<Movie>) Select.from(Movie.class)
                .where(Condition.prop("properties").eq(properties))
                .list();
    }
}
