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
import com.avevanjagmail.moviesapp.interfaces.OpenInformActivity;
import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.presenter.FavoriteFragmentPresenter;
import com.avevanjagmail.moviesapp.view.FavoriteFragmentView;

import java.util.ArrayList;


public class FavoriteTabFragment extends Fragment implements OpenInformActivity, FavoriteFragmentView {
    private static final String TAG = FavoriteTabFragment.class.getSimpleName();
    private RecyclerView rv;
    private RvMovieAdapter mMovieAdapter;
    private DbAdapterRv mDbAdapterRv;
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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        mFavoriteFragmentPresenter = new FavoriteFragmentPresenter();
        mFavoriteFragmentPresenter.setFavoriteFragmentView(this);
        mDbAdapterRv = new DbAdapterRv();
        mMovieAdapter = new RvMovieAdapter(this);
        rv.setAdapter(mMovieAdapter);
        Log.d(TAG, "onCreateView: ");
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMovieAdapter.clear();
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
        mDbAdapterRv.addNewMovies(localFavoriteMovies);
        rv.setAdapter(mDbAdapterRv);

    }

}


