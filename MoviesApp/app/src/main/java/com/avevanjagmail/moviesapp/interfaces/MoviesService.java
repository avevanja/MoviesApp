package com.avevanjagmail.moviesapp.interfaces;

import com.avevanjagmail.moviesapp.models.CastList;
import com.avevanjagmail.moviesapp.models.ListMovie;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.models.MoviesInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by John on 15.07.2016.
 */
public interface MoviesService {

    String URL= "http://api.themoviedb.org";



    @GET("3/movie/top_rated/?api_key=a143b2488bf72e7081edb871e0db3a7c")
    Call<ListMovie> getTopMovie(@Query("language") String lg, @Query("page") int page);

    @GET("3/movie/now_playing/?api_key=a143b2488bf72e7081edb871e0db3a7c")
    Call<ListMovie> getNewMovie(@Query("language") String lg, @Query("page") int page);

    @GET("3/movie/now_playing/?api_key=a143b2488bf72e7081edb871e0db3a7c")
    Call<ListMovie> getMovieInfo(@Query("language") String lg, @Query("page") int page);

    @GET("3/movie/{id}?api_key=a143b2488bf72e7081edb871e0db3a7c")
    Call<MoviesInfo> getMovieInfoFromId(@Path("id")String idMovie, @Query("language") String lg);

    @GET("3/movie/{id}/credits?api_key=a143b2488bf72e7081edb871e0db3a7c")
    Call<CastList> getCastList(@Path("id")String idMovie);

    @GET("3/search/movie/?api_key=a143b2488bf72e7081edb871e0db3a7c")
    Call<ListMovie> getSearchMovies(@Query("query")String MovieName);

    @GET("3/movie/{id}?api_key=a143b2488bf72e7081edb871e0db3a7c")
    Call<MovieApi> getMovieForFavorite(@Path("id")String idMovie, @Query("language") String lg);




}

