package com.avevanjagmail.moviesapp.presenter;

import android.util.Log;

import com.avevanjagmail.moviesapp.Interface.MoviesService;
import com.avevanjagmail.moviesapp.models.ListMovie;
import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.avevanjagmail.moviesapp.view.TopFragmentView;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopFragmentPresenter {
    private TopFragmentView topFragmentView;
    private ArrayList<Movie> localList = new ArrayList<>();
    private ArrayList<MovieApi> topListMovies = new ArrayList<>();
    private Movie movie;
    private static final String TAG = TopFragmentPresenter.class.getSimpleName();


    public void setTopFragmentView(TopFragmentView topFragmentView){
        this.topFragmentView = topFragmentView;
    }

    public void loadTopMovies(){
//        MoviesService mService = RetrofitUtil.getMoviesService();
//        Call<ListMovie> requestMovie = mService.getTopMovie("ru", 1);
//        requestMovie.enqueue(getCallback());
        RetrofitUtil.getMoviesService()
                .getTopMovie("ru", 1)
                .enqueue(getCallback());
    }


    public void loadMoreTopMovies(int current_page){
        MoviesService mService = RetrofitUtil.getMoviesService();
        Call<ListMovie> requestMovie = mService.getTopMovie("ru", current_page);
        requestMovie.enqueue(getCallbackLoadMore());
    }









    private Callback<ListMovie> getCallback() {
        Log.d(TAG, "getCallback");
        return new Callback<ListMovie>() {
            @Override
            public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
                Log.d(TAG, "getCallback onResponse");
                topListMovies = (ArrayList<MovieApi>)response.body().getResults();
                topFragmentView.setTopMovies(topListMovies);

                localList = (ArrayList<Movie>) Select.from(Movie.class)
                        .where(Condition.prop("properties").eq("Top"))
                        .list();
                for (Movie movie1 : localList) {
                    movie1.delete();

                }

                for (MovieApi movieApi : response.body().getResults()) {
                    movie = new Movie(movieApi, "Top");
                    movie.save();
                }

            }

            @Override
            public void onFailure(Call<ListMovie> call, Throwable t) {
                Log.e(TAG, "Eror" + t.getMessage());
                t.printStackTrace();
                localList = (ArrayList<Movie>) Select.from(Movie.class)
                        .where(Condition.prop("properties").eq("Top"))
                        .list();
                topFragmentView.setLocalTopMovies(localList);




            }
        };
    }
    private Callback<ListMovie> getCallbackLoadMore() {
        Log.d(TAG, "getCallback");
        return new Callback<ListMovie>() {
            @Override
            public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
                Log.d(TAG, "getCallback onResponse");
                topListMovies = (ArrayList<MovieApi>)response.body().getResults();
                topFragmentView.setMoreTopMovies(topListMovies);


            }

            @Override
            public void onFailure(Call<ListMovie> call, Throwable t) {
                Log.e(TAG, "Eror" + t.getMessage());
                t.printStackTrace();



            }
        };
    }



}
