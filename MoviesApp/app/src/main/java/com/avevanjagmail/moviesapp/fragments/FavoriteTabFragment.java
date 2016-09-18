package com.avevanjagmail.moviesapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.activities.InformActivity;
import com.avevanjagmail.moviesapp.adapters.LocalDbRecyclerAdapter;
import com.avevanjagmail.moviesapp.adapters.MovieRecyclerAdapter;
import com.avevanjagmail.moviesapp.interfaces.OpenInformActivity;
import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.presenter.FavoriteFragmentPresenter;
import com.avevanjagmail.moviesapp.view.FavoriteFragmentView;

import java.util.ArrayList;


public class FavoriteTabFragment extends Fragment implements OpenInformActivity, FavoriteFragmentView {
    private static final String TAG = FavoriteTabFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private MovieRecyclerAdapter mMovieAdapter;
    private LocalDbRecyclerAdapter mLocalDbRecyclerAdapter;
    private FavoriteFragmentPresenter mFavoriteFragmentPresenter;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public FavoriteTabFragment() {
    }


    public static FavoriteTabFragment newInstance(int sectionNumber) {
        FavoriteTabFragment fragment = new FavoriteTabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_movies, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv);

        LinearLayoutManager sLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(sLinearLayoutManager);
        mFavoriteFragmentPresenter = new FavoriteFragmentPresenter();
        mFavoriteFragmentPresenter.setFavoriteFragmentView(this);
        mLocalDbRecyclerAdapter = new LocalDbRecyclerAdapter();
        mMovieAdapter = new MovieRecyclerAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);
        Log.d(TAG, "onCreateView: ");
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        //в презентер
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMovieAdapter.clear();
                mLocalDbRecyclerAdapter.clear();
                mFavoriteFragmentPresenter.UpdateRemoteDb();
            }
        });
        mFavoriteFragmentPresenter.UpdateRemoteDb();

        return rootView;
    }



    @Override
    public void onClickOpen(String id, String url, String title) {
        InformActivity.start(id, url, title, getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        mFavoriteFragmentPresenter.onStop();
    }


    @Override
    public void setFavoriteMovies(MovieApi movieApi) {
        mMovieAdapter.addNewMovie(movieApi);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setLocalFavoriteMovies(ArrayList<Movie> localFavoriteMovies) {
        mLocalDbRecyclerAdapter.addNewMovies(localFavoriteMovies);
        mRecyclerView.setAdapter(mLocalDbRecyclerAdapter);
        mSwipeRefreshLayout.setRefreshing(false);

    }

}


