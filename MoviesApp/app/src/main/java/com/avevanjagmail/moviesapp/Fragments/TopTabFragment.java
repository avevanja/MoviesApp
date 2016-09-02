package com.avevanjagmail.moviesapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avevanjagmail.moviesapp.EndlessRecyclerOnScrollListener;
import com.avevanjagmail.moviesapp.Interface.OpenInformActivity;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.activities.InformActivity;
import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.presenter.TopFragmentPresenter;
import com.avevanjagmail.moviesapp.view.TopFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10.07.2016.
 */
public class TopTabFragment extends Fragment implements OpenInformActivity, TopFragmentView {
    List<Movie> localList;


    private RecyclerView rv;


    private LinearLayoutManager llm;
    private RvMovieAdapter mMovieAdapter;
    private DbAdapterRv mDbAdapterRv;

    private TopFragmentPresenter topFragmentPresenter;


    public TopTabFragment() {

    }

    public static TopTabFragment newInstance() {
        TopTabFragment fragment = new TopTabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View parentView = inflater.inflate(R.layout.fragment_topmovie, container, false);

        rv = (RecyclerView) parentView.findViewById(R.id.rv);
        localList = new ArrayList<>();
        llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        topFragmentPresenter = new TopFragmentPresenter();
        mDbAdapterRv = new DbAdapterRv();
        topFragmentPresenter.setTopFragmentView(this);
        mMovieAdapter = new RvMovieAdapter(this);
        rv.setAdapter(mMovieAdapter);


//        MoviesService mService = RetrofitUtil.getMoviesService();
//        Call<ListMovie> requestMovie = mService.getTopMovie("ru", 1);


//        requestMovie.enqueue(getCallback());

        rv.setOnScrollListener(new EndlessRecyclerOnScrollListener(llm) {
            @Override
            public void onLoadMore(int current_page) {
                topFragmentPresenter.loadMoreTopMovies(current_page);
            }
        });
        topFragmentPresenter.loadTopMovies();

        return parentView;
    }


//    private Callback<ListMovie> getCallback() {
//        Log.d(TAG, "getCallback");
//        return new Callback<ListMovie>() {
//            @Override
//            public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
//                Log.d(TAG, "getCallback onResponse");
//                mMovieAdapter.addNewMovies(response.body().getResults());
//                localList = Select.from(Movie.class)
//                        .where(Condition.prop("properties").eq("Top"))
//                        .list();
//                for (Movie movie1 : localList) {
//                    movie1.delete();
//
//                }
//
//                for (MovieApi movieApi : response.body().getResults()) {
//                     movie = new Movie(movieApi, "Top");
//                     movie.save();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ListMovie> call, Throwable t) {
//                Log.e(TAG, "Eror" + t.getMessage());
//                t.printStackTrace();
//
//                localList = Select.from(Movie.class)
//                        .where(Condition.prop("properties").eq("Top"))
//                                .list();
//
////                localList = Movie.listAll(Movie.class);
//
//                mDbAdapterRv = new DbAdapterRv();
//                mDbAdapterRv.addNewMovies(localList);
//                rv.setAdapter(mDbAdapterRv);
//                Toast.makeText(getActivity().getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
//
//            }
//        };
//    }
//    private Callback<ListMovie> getCallbackLoadMore() {
//        Log.d(TAG, "getCallback");
//        return new Callback<ListMovie>() {
//            @Override
//            public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
//                Log.d(TAG, "getCallback onResponse");
//                mMovieAdapter.addNewMovies(response.body().getResults());
//
//            }
//
//            @Override
//            public void onFailure(Call<ListMovie> call, Throwable t) {
//                Log.e(TAG, "Eror" + t.getMessage());
//                t.printStackTrace();
//
//                Toast.makeText(getActivity().getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
//
//            }
//        };
//    }

    @Override
    public void onClickOpen(String id, String url, String title) {
        InformActivity.start(id, url, title, getContext());

    }

    @Override
    public void setTopMovies(ArrayList<MovieApi> topMovies) {

        mMovieAdapter.addNewMovies(topMovies);
    }

    @Override
    public void setMoreTopMovies(ArrayList<MovieApi> topMovies) {
        mMovieAdapter.addNewMovies(topMovies);

    }

    @Override
    public void setLocalTopMovies(ArrayList<Movie> localTopMovies) {
        mDbAdapterRv.addNewMovies(localTopMovies);
        rv.setAdapter(mDbAdapterRv);
    }
}


