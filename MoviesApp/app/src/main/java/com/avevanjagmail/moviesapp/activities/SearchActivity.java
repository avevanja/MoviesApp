package com.avevanjagmail.moviesapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.avevanjagmail.moviesapp.Fragments.RvMovieAdapter;
import com.avevanjagmail.moviesapp.Interface.MoviesService;
import com.avevanjagmail.moviesapp.Interface.OpenInformActivity;
import com.avevanjagmail.moviesapp.Models.ListMovie;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by paulg on 11.08.2016.
 */
public class SearchActivity extends AppCompatActivity implements OpenInformActivity {
    private LinearLayoutManager llm;
    public RvMovieAdapter mMovieAdapter;
    private RecyclerView rv;
    private static final String TAG = SearchActivity.class.getSimpleName();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setTitle(getIntent().getStringExtra("query"));
        rv = (RecyclerView) findViewById(R.id.rv_searc);

        llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);


        MoviesService mService = RetrofitUtil.getMoviesService();

        Call<ListMovie> requestMovie = mService.getSearchMovies(getIntent().getStringExtra("query"));

        mMovieAdapter = new RvMovieAdapter(this);
        rv.setAdapter(mMovieAdapter);

        requestMovie.enqueue(getCallback());
    }
    private Callback<ListMovie> getCallback() {
        Log.d(TAG, "getCallback");
        return new Callback<ListMovie>() {
            @Override
            public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
                Log.d(TAG, "getCallback onResponse");
                mMovieAdapter.addNewMovies(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ListMovie> call, Throwable t) {
                Log.e(TAG, "Eror" + t.getMessage());
                t.printStackTrace();
            }
        };
    }

    @Override
    public void onClickOpen(String id, String url, String title) {
        InformActivity.start(id, url, title, this);
    }
}

