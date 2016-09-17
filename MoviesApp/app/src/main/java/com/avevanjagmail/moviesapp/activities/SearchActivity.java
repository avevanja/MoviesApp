package com.avevanjagmail.moviesapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.adapters.MovieRecyclerAdapter;
import com.avevanjagmail.moviesapp.interfaces.OpenInformActivity;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.presenter.SearchActivityPresenter;
import com.avevanjagmail.moviesapp.view.SearchActivityView;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity implements OpenInformActivity, SearchActivityView {
    private LinearLayoutManager llm;
    public MovieRecyclerAdapter mMovieAdapter;
    private RecyclerView rv;
    private SearchActivityPresenter mSearchActivityPresenter;
    private static final String TAG = SearchActivity.class.getSimpleName();
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
        rv = (RecyclerView) findViewById(R.id.search_activity_rv);

        llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        mSearchActivityPresenter.loadSearchMovies(getIntent().getStringExtra("query"));


        mMovieAdapter = new MovieRecyclerAdapter(this);
        rv.setAdapter(mMovieAdapter);


    }

    @Override
    public void onClickOpen(String id, String url, String title) {
        InformActivity.start(id, url, title, this);
    }

    @Override
    public void setSearchMoviesList(ArrayList<MovieApi> searchMoviesList) {
        if(searchMoviesList.size() == 0){
            Toast.makeText(SearchActivity.this, R.string.error_search, Toast.LENGTH_SHORT).show();
        }
        mMovieAdapter.addNewMovies(searchMoviesList);

    }

    @Override
    public Context getContext() {
        return null;
    }
}

