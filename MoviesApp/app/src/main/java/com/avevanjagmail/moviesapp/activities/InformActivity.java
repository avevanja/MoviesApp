package com.avevanjagmail.moviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avevanjagmail.moviesapp.Interface.MoviesService;
import com.avevanjagmail.moviesapp.Models.Cast;
import com.avevanjagmail.moviesapp.Models.CastList;
import com.avevanjagmail.moviesapp.Models.Genre;
import com.avevanjagmail.moviesapp.Models.MoviesInfo;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
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
    private TextView TextTet, mOverviewTextView, mNameCast, mNameCast1, mNameCast2, mNameCast3, mNameCast4, mNameCast5, mDataRealise, mCountryName;
    private ImageView TitleImageView, mCastFoto, mCastFoto1, mCastFoto2, mCastFoto3, mCastFoto4, mCastFoto5;
    private Toolbar toolbarInformActivity;
    private ArrayList<Genre> mListMovie;
    private ArrayList<Cast> mListCast;






    private static final String MOVIE_ID = "movie.id";
    private static final String URL_Image = "url";
    private static final String TITLE = "movie_title";
    private boolean showingFirst;

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
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_favourite_btn);
        showingFirst = true;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showingFirst == true) {
                    fab.setImageResource(R.drawable.ic_favorite_white_24dp);
                    showingFirst = false;
                }
                else {
                    fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    showingFirst = true;

                }
            }
        });
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
        mCastFoto = (ImageView)findViewById(R.id.cast_foto);
        mCastFoto1 = (ImageView)findViewById(R.id.cast_foto1);
        mCastFoto2 = (ImageView)findViewById(R.id.cast_foto2);
        mCastFoto3 = (ImageView)findViewById(R.id.cast_foto3);
        mCastFoto4 = (ImageView)findViewById(R.id.cast_foto4);
        mCastFoto5 = (ImageView)findViewById(R.id.cast_foto5);
        TextTet = (TextView)findViewById(R.id.inform_movie_text);
        mOverviewTextView = (TextView)findViewById(R.id.text_overview);
        mNameCast = (TextView)findViewById(R.id.name_cast_text_view);
        mNameCast1 = (TextView)findViewById(R.id.name_cast_text_view1);
        mNameCast2 = (TextView)findViewById(R.id.name_cast_text_view2);
        mNameCast3 = (TextView)findViewById(R.id.name_cast_text_view3);
        mNameCast4 = (TextView)findViewById(R.id.name_cast_text_view4);
        mNameCast5 = (TextView)findViewById(R.id.name_cast_text_view5);
        mDataRealise = (TextView)findViewById(R.id.release_date_text_view);
        mCountryName = (TextView)findViewById(R.id.countrу_text_view);
        String text = getIntent().getStringExtra(MOVIE_ID);

        Picasso.with(this).load("https://image.tmdb.org/t/p/w533_and_h300_bestv2" + getIntent().getStringExtra(URL_Image)).
                error(R.drawable.ava).resize(717, 400).into(TitleImageView);


        MoviesService mService = RetrofitUtil.getMoviesService();
        Call<MoviesInfo> requestMovie = mService.getMovieInfoFromId(text, "ru");
        requestMovie.enqueue(getCallback());
        MoviesService mServiceCastList = RetrofitUtil.getMoviesService();
        Call<CastList> requestCastList = mServiceCastList.getCastList(text);
        requestCastList.enqueue(getCastListCallback());

    }
    private Callback<CastList> getCastListCallback(){
        return new Callback<CastList>() {
            @Override
            public void onResponse(Call<CastList> call, Response<CastList> response) {
                Log.d(TAG, "getCallback onResponse");

                mListCast = (ArrayList<Cast>) response.body().getCast();
                if (mListCast.size()>=6) {


                    mNameCast.setText(mListCast.get(0).getName());
                    Picasso.with(mCastFoto.getContext()).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + mListCast.get(0).getProfilePath()).
                            error(R.drawable.ava).into(mCastFoto);
                    mNameCast1.setText(mListCast.get(1).getName());
                    Picasso.with(mCastFoto.getContext()).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + mListCast.get(1).getProfilePath()).
                            error(R.drawable.ava).into(mCastFoto1);
                    mNameCast2.setText(mListCast.get(2).getName());
                    Picasso.with(mCastFoto.getContext()).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + mListCast.get(2).getProfilePath()).
                            error(R.drawable.ava).into(mCastFoto2);
                    mNameCast3.setText(mListCast.get(3).getName());
                    Picasso.with(mCastFoto.getContext()).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + mListCast.get(3).getProfilePath()).
                            error(R.drawable.ava).into(mCastFoto3);
                    mNameCast4.setText(mListCast.get(4).getName());
                    Picasso.with(mCastFoto.getContext()).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + mListCast.get(4).getProfilePath()).
                            error(R.drawable.ava).into(mCastFoto4);
                    mNameCast5.setText(mListCast.get(5).getName());
                    Picasso.with(mCastFoto.getContext()).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + mListCast.get(5).getProfilePath()).
                            error(R.drawable.ava).into(mCastFoto5);
                }



            }

            @Override
            public void onFailure(Call<CastList> call, Throwable t) {
                Log.e(TAG, "Eror" + t.getMessage());
                t.printStackTrace();

            }
        };
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
                mDataRealise.setText(response.body().getReleaseDate());
                mCountryName.setText(response.body().getProductionCountries().get(0).getName());


            }

            @Override
            public void onFailure(Call<MoviesInfo> call, Throwable t) {
                Log.e(TAG, "Eror" + t.getMessage());
                t.printStackTrace();
            }
        };
    }
}
