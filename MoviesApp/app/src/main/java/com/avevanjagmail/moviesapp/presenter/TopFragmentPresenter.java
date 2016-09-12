package com.avevanjagmail.moviesapp.presenter;

import android.util.Log;

import com.avevanjagmail.moviesapp.managers.DBManager;
import com.avevanjagmail.moviesapp.models.ListMovie;
import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.avevanjagmail.moviesapp.view.TopFragmentView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopFragmentPresenter {
    private TopFragmentView topFragmentView;
    private ArrayList<Movie> localList = new ArrayList<>();
    private ArrayList<MovieApi> topListMovies = new ArrayList<>();
    private static final String TAG = TopFragmentPresenter.class.getSimpleName();


    public void setTopFragmentView(TopFragmentView topFragmentView){
        this.topFragmentView = topFragmentView;
    }

    public void loadTopMovies(){
        RetrofitUtil.getMoviesService()
                .getTopMovie("ru", 1)
                .enqueue(getCallback());
    }


    public void loadMoreTopMovies(int current_page){
        RetrofitUtil.getMoviesService()
                .getTopMovie("ru", current_page)
                .enqueue(getCallbackLoadMore());
    }





    private Callback<ListMovie> getCallback() {
        Log.d(TAG, "getCallback");
        return new Callback<ListMovie>() {
            @Override
            public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
                Log.d(TAG, "getCallback onResponse");
                topListMovies = response.body().getResults();
                topFragmentView.setTopMovies(topListMovies);

                localList = DBManager.getLocalListMovie("Top");
                for (Movie movie1 : localList) {
                    movie1.delete();
                }

                for (MovieApi movieApi : response.body().getResults()) {
                    DBManager.save(new Movie(movieApi, "Top"));
                }

            }

            @Override
            public void onFailure(Call<ListMovie> call, Throwable t) {
                Log.e(TAG, "Error" + t.getMessage());
                t.printStackTrace();
                localList = DBManager.getLocalListMovie("Top");
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
                topListMovies = response.body().getResults();
                topFragmentView.setMoreTopMovies(topListMovies);



            }

            @Override
            public void onFailure(Call<ListMovie> call, Throwable t) {
                Log.e(TAG, "Error" + t.getMessage());
                t.printStackTrace();
            }
        };
    }



}
