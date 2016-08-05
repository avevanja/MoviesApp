package com.avevanjagmail.moviesapp.Interface;

import com.avevanjagmail.moviesapp.Models.ListMovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by John on 15.07.2016.
 */
public interface MoviesService {
    String URL = "http://api.themoviedb.org";

    @GET("3/movie/top_rated/?api_key=a143b2488bf72e7081edb871e0db3a7c")
    Call<ListMovie> getTopName(@Query("language") String lg, @Query("page") int page);

    @GET("3/movie/now_playing/?api_key=a143b2488bf72e7081edb871e0db3a7c")
    Call<ListMovie> getNewMovie(@Query("language") String lg, @Query("page") int page);

    @GET("3/movie/now_playing/?api_key=a143b2488bf72e7081edb871e0db3a7c")
    Call<ListMovie> getMovieInfo(@Query("language") String lg, @Query("page") int page);



}

