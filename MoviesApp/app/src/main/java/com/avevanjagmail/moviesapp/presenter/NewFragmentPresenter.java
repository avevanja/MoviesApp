package com.avevanjagmail.moviesapp.presenter;

import android.util.Log;

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.managers.DBManager;
import com.avevanjagmail.moviesapp.models.ListMovie;
import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.avevanjagmail.moviesapp.view.NewFragmentView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewFragmentPresenter {
    private NewFragmentView mNewFragmentView;
    private ArrayList<Movie> localList = new ArrayList<>();
    private ArrayList<MovieApi> newListMovies = new ArrayList<>();
    private Movie movie;
    private static final String TAG = TopFragmentPresenter.class.getSimpleName();


    public void setNewFragmentView(NewFragmentView mNewFragmentView) {
        this.mNewFragmentView = mNewFragmentView;
    }

    public void loadNewMovies() {
        RetrofitUtil.getMoviesService()
                .getNewMovie(mNewFragmentView.getContext().getString(R.string.query_lng), 1)
                .enqueue(getCallback());
    }



    public void loadMoreNewMovies(int current_page) {
        RetrofitUtil.getMoviesService()
                .getNewMovie(mNewFragmentView.getContext().getString(R.string.query_lng), current_page)
                .enqueue(getCallbackLoadMore());

    }


    private Callback<ListMovie> getCallback() {
        Log.d(TAG, "getCallback");
        return new Callback<ListMovie>() {
            @Override
            public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
                Log.d(TAG, "getCallback onResponse");
                newListMovies = response.body().getResults();
                mNewFragmentView.setNewMovies(newListMovies);

                localList = DBManager.getLocalListMovie("New");
                for (Movie movie1 : localList) {
                    movie1.delete();
                }

                for (MovieApi movieApi : response.body().getResults()) {
                    movie = new Movie(movieApi, "New");
                    movie.save();
                }

            }

            @Override
            public void onFailure(Call<ListMovie> call, Throwable t) {
                Log.e(TAG, "Error" + t.getMessage());
                t.printStackTrace();
                localList = DBManager.getLocalListMovie("New");
                mNewFragmentView.setLocalNewMovies(localList);
            }
        };
    }

    private Callback<ListMovie> getCallbackLoadMore() {
        Log.d(TAG, "getCallback");
        return new Callback<ListMovie>() {
            @Override
            public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
                Log.d(TAG, "getCallback onResponse");
                newListMovies = response.body().getResults();
                mNewFragmentView.setMoreNewMovies(newListMovies);


            }

            @Override
            public void onFailure(Call<ListMovie> call, Throwable t) {
                Log.e(TAG, "Error" + t.getMessage());
                t.printStackTrace();


            }
        };
    }


}
