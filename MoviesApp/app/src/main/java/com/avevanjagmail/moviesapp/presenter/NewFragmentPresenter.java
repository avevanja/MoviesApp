package com.avevanjagmail.moviesapp.presenter;

import android.util.Log;

import com.avevanjagmail.moviesapp.interfaces.MoviesService;
import com.avevanjagmail.moviesapp.models.ListMovie;
import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.avevanjagmail.moviesapp.view.NewFragmentView;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by paulg on 31.08.2016.
 */
public class NewFragmentPresenter {
    private NewFragmentView newFragmentView;
    private ArrayList<Movie> localList = new ArrayList<>();
    private ArrayList<MovieApi> newListMovies = new ArrayList<>();
    private Movie movie;
    private static final String TAG = TopFragmentPresenter.class.getSimpleName();


    public void setNewFragmentView(NewFragmentView newFragmentView){
        this.newFragmentView = newFragmentView;
    }

    public void loadNewMovies(){
        MoviesService mService = RetrofitUtil.getMoviesService();
        Call<ListMovie> requestMovie = mService.getNewMovie("ru", 1);
        requestMovie.enqueue(getCallback());
    }


    public void loadMoreNewMovies(int current_page){
        MoviesService mService = RetrofitUtil.getMoviesService();
        Call<ListMovie> requestMovie = mService.getNewMovie("ru", current_page);
        requestMovie.enqueue(getCallbackLoadMore());
    }









    private Callback<ListMovie> getCallback() {
        Log.d(TAG, "getCallback");
        return new Callback<ListMovie>() {
            @Override
            public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
                Log.d(TAG, "getCallback onResponse");
                newListMovies = (ArrayList<MovieApi>)response.body().getResults();
                newFragmentView.setNewMovies(newListMovies);

                localList = (ArrayList<Movie>) Select.from(Movie.class)
                        .where(Condition.prop("properties").eq("New"))
                        .list();
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
                Log.e(TAG, "Eror" + t.getMessage());
                t.printStackTrace();
                localList = (ArrayList<Movie>) Select.from(Movie.class)
                        .where(Condition.prop("properties").eq("New"))
                        .list();
                newFragmentView.setLocalNewMovies(localList);




            }
        };
    }
    private Callback<ListMovie> getCallbackLoadMore() {
        Log.d(TAG, "getCallback");
        return new Callback<ListMovie>() {
            @Override
            public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
                Log.d(TAG, "getCallback onResponse");
                newListMovies = (ArrayList<MovieApi>)response.body().getResults();
                newFragmentView.setMoreNewMovies(newListMovies);


            }

            @Override
            public void onFailure(Call<ListMovie> call, Throwable t) {
                Log.e(TAG, "Eror" + t.getMessage());
                t.printStackTrace();



            }
        };
    }



}
