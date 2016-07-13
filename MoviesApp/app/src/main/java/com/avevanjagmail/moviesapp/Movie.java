package com.avevanjagmail.moviesapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 12.07.2016.
 */
public class Movie {
    public String getGanre() {
        return ganre;
    }

    public void setGanre(String ganre) {
        this.ganre = ganre;
    }

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
    String ganre;
    String top;
    int imageStarId;

    public int getImageStarId() {
        return imageStarId;
    }

    public void setImageStarId(int imageStarId) {
        this.imageStarId = imageStarId;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public Movie(String name, String ganre, int posterId, int imageStarId, String top) {
        this.name = name;
        this.posterId = posterId;
        this.ganre = ganre;
        this.imageStarId =imageStarId;
        this.top = top;
    }

    public static List<Movie> initializeData(){
        ArrayList movies = new ArrayList();

        movies.add(new Movie("Побег из Шоушенка", "драма, криминал", R.drawable.ic_movies_top, R.mipmap.ic_star_rate_black_18dp, "8.3"));
        movies.add(new Movie("Крестный отец", "драма, криминал", R.drawable.gf, R.mipmap.ic_star_rate_black_18dp, "8.3"));
        movies.add(new Movie("Интерстеллар", "драма, приключение", R.drawable.in, R.mipmap.ic_star_rate_black_18dp, "8.3"));
        movies.add(new Movie("1+1", "драма, комедия", R.drawable.onefff, R.mipmap.ic_star_rate_black_18dp, "8.3"));
        movies.add(new Movie("Бойцовский клуб", "драма", R.drawable.fc,R.mipmap.ic_star_rate_black_18dp, "8.3"));
        return movies;

    }
}
