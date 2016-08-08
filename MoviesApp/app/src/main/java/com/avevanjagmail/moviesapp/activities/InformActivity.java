package com.avevanjagmail.moviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avevanjagmail.moviesapp.Interface.MoviesService;
import com.avevanjagmail.moviesapp.Models.Genre;
import com.avevanjagmail.moviesapp.Models.MoviesInfo;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by paulg on 03.08.2016.
 */
public class InformActivity extends AppCompatActivity {
    private static final String TAG = "InformActivity" ;
    private TextView TextTet, mOverviewTextView;
    private ImageView TitleImageView;
    private Toolbar toolbarInformActivity;
    private ArrayList<Genre> mListMovie;




    private static final String MOVIE_ID = "movie.id";
    private static final String URL_Image = "url";
    private static final String TITLE = "movie_title";

    public static void start(String movieId, String url, String title, Context context) {
        Intent starter = new Intent(context, InformActivity.class);
        starter.putExtra(MOVIE_ID, movieId);
        starter.putExtra(URL_Image, url);
        starter.putExtra(TITLE, title);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);
        toolbarInformActivity = (Toolbar) findViewById(R.id.toolbar_inf_act);
        setSupportActionBar(toolbarInformActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbarInformActivity.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setTitle(getIntent().getStringExtra(TITLE));
        TitleImageView = (ImageView)findViewById(R.id.expandedImage);
        TextTet = (TextView)findViewById(R.id.inform_movie_text);
        mOverviewTextView = (TextView)findViewById(R.id.text_overview);
        String text = getIntent().getStringExtra(MOVIE_ID);
//        TextTet.setText(text.toString());
        Picasso.with(this).load("https://image.tmdb.org/t/p/w533_and_h300_bestv2" + getIntent().getStringExtra(URL_Image)).
                error(R.drawable.ava).resize(717, 400).into(TitleImageView);
        Gson gson = new GsonBuilder().create();

        MoviesService mService = RetrofitUtil.getMoviesService();
        Call<MoviesInfo> requestMovie = mService.getMovieInfoFromId(text, "ru");
        requestMovie.enqueue(getCallback());
//        TextTet.setText("bla");
    }
    private Callback<MoviesInfo> getCallback() {
        Log.d(TAG, "getCallback");
        return new Callback<MoviesInfo>() {
            @Override
            public void onResponse(Call<MoviesInfo> call, Response<MoviesInfo> response) {
                Log.d(TAG, "getCallback onResponse");
                mListMovie = (ArrayList<Genre>) response.body().getGenres();
                for (Genre genre : mListMovie)
                {
                    TextTet.setText(genre.getName() + " ");
                }
                mOverviewTextView.setText(response.body().getOverview());

//                TextTet.setText(mGenre.getName());
//                TextTet.setText("bla");


            }

            @Override
            public void onFailure(Call<MoviesInfo> call, Throwable t) {
                Log.e(TAG, "Eror" + t.getMessage());
                t.printStackTrace();
            }
        };
    }
}
