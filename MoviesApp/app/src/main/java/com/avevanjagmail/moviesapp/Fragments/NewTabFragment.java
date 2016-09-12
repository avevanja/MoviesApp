package com.avevanjagmail.moviesapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avevanjagmail.moviesapp.EndlessRecyclerOnScrollListener;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.activities.InformActivity;
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
    private RecyclerView rv;
    private static final String TAG = NewTabFragment.class.getSimpleName();
    private LinearLayoutManager llm;
    private RvMovieAdapter mMovieAdapter;
    private DbAdapterRv mDbAdapterRv;
    private NewFragmentPresenter mNewFragmentPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

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
        View parentView = inflater.inflate(R.layout.fragment_main, container, false);
        mNewFragmentPresenter = new NewFragmentPresenter();
        mNewFragmentPresenter.setNewFragmentView(this);

        rv = (RecyclerView) parentView.findViewById(R.id.rv);
        mSwipeRefreshLayout = (SwipeRefreshLayout) parentView.findViewById(R.id.swipeRefreshLayout);

        llm = new LinearLayoutManager(getContext());

        rv.setLayoutManager(llm);


        mDbAdapterRv = new DbAdapterRv();

        mMovieAdapter = new RvMovieAdapter(this);
        rv.setAdapter(mMovieAdapter);
        mNewFragmentPresenter.loadNewMovies();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMovieAdapter.clear();
                mNewFragmentPresenter.loadNewMovies();
            }
        });



        rv.setOnScrollListener(new EndlessRecyclerOnScrollListener(llm) {
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
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void setMoreNewMovies(ArrayList<MovieApi> newMovies) {
        mMovieAdapter.addNewMovies(newMovies);

    }

    @Override
    public void setLocalNewMovies(ArrayList<Movie> localNewMovies) {
        mDbAdapterRv.addNewMovies(localNewMovies);
        rv.setAdapter(mDbAdapterRv);

    }
}

