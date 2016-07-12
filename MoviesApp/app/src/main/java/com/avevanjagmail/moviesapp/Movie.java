package com.avevanjagmail.moviesapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 12.07.2016.
 */
public class Movie {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosterId() {
        return posterId;
    }

    public void setPosterId(int posterId) {
        this.posterId = posterId;
    }

    String name;
    int posterId;

    public Movie(String name, int posterId) {
        this.name = name;
        this.posterId = posterId;
    }

    public static List<Movie> initializeData(){
        ArrayList movies = new ArrayList();

        movies.add(new Movie("Побег из Шоушенка", R.drawable.ic_movies_top));
        movies.add(new Movie("Побег из Шоушенка", R.drawable.ic_movies_top));
        movies.add(new Movie("Побег из Шоушенка", R.drawable.ic_movies_top));
        movies.add(new Movie("Побег из Шоушенка", R.drawable.ic_movies_top));
        movies.add(new Movie("Побег из Шоушенка", R.drawable.ic_movies_top));
        return movies;

    }
}
