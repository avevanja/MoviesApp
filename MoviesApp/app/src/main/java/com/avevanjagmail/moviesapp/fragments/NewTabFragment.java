package com.avevanjagmail.moviesapp.fragments;

import android.os.Bundle;
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
import com.avevanjagmail.moviesapp.presenter.NewFragmentPresenter;
import com.avevanjagmail.moviesapp.view.NewFragmentView;

import java.util.ArrayList;

/**
 * Created by John on 10.07.2016.
 */
public class NewTabFragment extends Fragment implements OpenInformActivity, NewFragmentView {
    private RecyclerView mRecyclerView;
    private static final String TAG = NewTabFragment.class.getSimpleName();
    private LinearLayoutManager mLinearLayoutManager;
    private MovieRecyclerAdapter mMovieAdapter;
    private LocalDbRecyclerAdapter mLocalDbRecyclerAdapter;
    private NewFragmentPresenter mNewFragmentPresenter;


    public NewTabFragment() {
    }

    public static NewTabFragment newInstance() {
        NewTabFragment fragment = new NewTabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_new_movies, container, false);
        mNewFragmentPresenter = new NewFragmentPresenter();
        mNewFragmentPresenter.setNewFragmentView(this);
        mRecyclerView = (RecyclerView) parentView.findViewById(R.id.new_movie_rv);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mLocalDbRecyclerAdapter = new LocalDbRecyclerAdapter();
        mMovieAdapter = new MovieRecyclerAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);
        mNewFragmentPresenter.loadNewMovies();



        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                mNewFragmentPresenter.loadMoreNewMovies(current_page);
            }
        });

        return parentView;
    }



    @Override
    public void onClickOpen(String id, String url, String title) {
        InformActivity.start(id, url, title, getContext());
    }

    @Override
    public void setNewMovies(ArrayList<MovieApi> newMovies) {
        mMovieAdapter.addNewMovies(newMovies);


    }

    @Override
    public void setMoreNewMovies(ArrayList<MovieApi> newMovies) {
        mMovieAdapter.addNewMovies(newMovies);

    }

    @Override
    public void setLocalNewMovies(ArrayList<Movie> localNewMovies) {
        mLocalDbRecyclerAdapter.addNewMovies(localNewMovies);
        mRecyclerView.setAdapter(mLocalDbRecyclerAdapter);

    }
}

