package com.avevanjagmail.moviesapp.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avevanjagmail.moviesapp.utils.EndlessRecyclerOnScrollListener;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.activities.InformActivity;
import com.avevanjagmail.moviesapp.adapters.LocalDbRecyclerAdapter;
import com.avevanjagmail.moviesapp.adapters.MovieRecyclerAdapter;
import com.avevanjagmail.moviesapp.interfaces.OpenInformActivity;
import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.presenter.TopFragmentPresenter;
import com.avevanjagmail.moviesapp.view.TopFragmentView;

import java.util.ArrayList;

/**
 * Created by John on 10.07.2016.
 */
public class TopTabFragment extends Fragment implements OpenInformActivity, TopFragmentView {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private MovieRecyclerAdapter mMovieAdapter;
    private LocalDbRecyclerAdapter mLocalDbRecyclerAdapter;
    private TopFragmentPresenter mTopFragmentPresenter;
    private ProgressDialog mProgredDIalog;



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

        View parentView = inflater.inflate(R.layout.fragment_top_movies, container, false);

        mRecyclerView = (RecyclerView) parentView.findViewById(R.id.top_movies_rv);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mTopFragmentPresenter = new TopFragmentPresenter();
        mLocalDbRecyclerAdapter = new LocalDbRecyclerAdapter();
        mTopFragmentPresenter.setTopFragmentView(this);
        mMovieAdapter = new MovieRecyclerAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);
        mProgredDIalog = new ProgressDialog(getContext());
        mProgredDIalog.show();
        mTopFragmentPresenter.loadTopMovies();






        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                mTopFragmentPresenter.loadMoreTopMovies(current_page);
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
        mLocalDbRecyclerAdapter.addNewMovies(localTopMovies);
        mRecyclerView.setAdapter(mLocalDbRecyclerAdapter);
//        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void stopProgress() {
        mProgredDIalog.dismiss();
    }
}


