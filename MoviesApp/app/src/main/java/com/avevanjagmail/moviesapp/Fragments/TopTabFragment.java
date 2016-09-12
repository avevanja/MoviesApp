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
import com.avevanjagmail.moviesapp.interfaces.OpenInformActivity;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.activities.InformActivity;
import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.presenter.TopFragmentPresenter;
import com.avevanjagmail.moviesapp.view.TopFragmentView;

import java.util.ArrayList;

/**
 * Created by John on 10.07.2016.
 */
public class TopTabFragment extends Fragment implements OpenInformActivity, TopFragmentView {
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

        View parentView = inflater.inflate(R.layout.fragment_main, container, false);

        rv = (RecyclerView) parentView.findViewById(R.id.rv);
        llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        topFragmentPresenter = new TopFragmentPresenter();
        mDbAdapterRv = new DbAdapterRv();
        topFragmentPresenter.setTopFragmentView(this);
        mMovieAdapter = new RvMovieAdapter(this);
        rv.setAdapter(mMovieAdapter);
        topFragmentPresenter.loadTopMovies();





        rv.setOnScrollListener(new EndlessRecyclerOnScrollListener(llm) {
            @Override
            public void onLoadMore(int current_page) {
                topFragmentPresenter.loadMoreTopMovies(current_page);
            }
        });


        return parentView;
    }




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


