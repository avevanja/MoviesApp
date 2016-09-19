package com.avevanjagmail.moviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.adapters.MovieRecyclerAdapter;
import com.avevanjagmail.moviesapp.interfaces.OpenInformActivity;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.presenter.SearchActivityPresenter;
import com.avevanjagmail.moviesapp.view.SearchActivityView;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity implements OpenInformActivity, SearchActivityView {
    private LinearLayoutManager mLinearLayoutManager;
    public MovieRecyclerAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private SearchActivityPresenter mSearchActivityPresenter;
    private static final String TAG = SearchActivity.class.getSimpleName();
    public static void start(Context context, String query) {
        Intent starter = new Intent(context, SearchActivity.class);
        starter.putExtra("query", query);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mSearchActivityPresenter = new SearchActivityPresenter();
        mSearchActivityPresenter.setSearchActivityView(this);
        setTitle(getIntent().getStringExtra("query"));
        mRecyclerView = (RecyclerView) findViewById(R.id.search_activity_rv);
        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mSearchActivityPresenter.loadSearchMovies(getIntent().getStringExtra("query"));
        mMovieAdapter = new MovieRecyclerAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);


    }


    @Override
    public void onClickOpen(String id, String url, String title) {
        InformActivity.start(id, url, title, this);
    }


    @Override
    public void setSearchMoviesList(ArrayList<MovieApi> searchMoviesList) {
        mMovieAdapter.addNewMovies(searchMoviesList);

    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchActivityPresenter.onDestroy();
    }
}

