package com.avevanjagmail.moviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.models.Cast;
import com.avevanjagmail.moviesapp.models.Genre;
import com.avevanjagmail.moviesapp.models.MoviesInfo;
import com.avevanjagmail.moviesapp.presenter.InformActivityPresenter;
import com.avevanjagmail.moviesapp.view.InformActivityView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class InformActivity extends AppCompatActivity implements InformActivityView {
    private static final String TAG = "InformActivity";
    private TextView mGenreTextView, mOverviewTextView, mNameCast, mNameCast1, mNameCast2, mNameCast3, mNameCast4, mNameCast5, mDataRealise, mCountryName;
    private ImageView mTitleImageView, mCastPhoto, mCastPhoto1, mCastPhoto2, mCastPhoto3, mCastPhoto4, mCastPhoto5;
    private Toolbar mToolbarInformActivity;
    private ArrayList<Genre> mListMovie;
    private static final String MOVIE_ID = "movie.id";
    private static final String URL_IMAGE = "url";
    private static final String TITLE = "movie_title";
    private boolean showingFirst;
    private FloatingActionButton mFloatingActionButton;
    private InformActivityPresenter mInformActivityPresenter;



    public static void start(String movieId, String url, String title, Context context) {
        Intent starter = new Intent(context, InformActivity.class);
        starter.putExtra(MOVIE_ID, movieId);
        starter.putExtra(URL_IMAGE, url);
        starter.putExtra(TITLE, title);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_favourite_btn);
        mInformActivityPresenter = new InformActivityPresenter();
        mInformActivityPresenter.setInformActivityView(this);


        showingFirst = true;


        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (showingFirst) {
                    mFloatingActionButton.setImageResource(R.drawable.ic_favorite_white_24dp);
                    mInformActivityPresenter.addFavoriteMovieInRemoteDb(getIntent().getStringExtra(MOVIE_ID));
                    showingFirst = false;

                } else {
                    mFloatingActionButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    mInformActivityPresenter.deleteFavoriteMovieFromRemoteDb(getIntent().getStringExtra(MOVIE_ID));
                    showingFirst = true;


                }
            }

        });
        mInformActivityPresenter.checkMovieForFavorite(getIntent().getStringExtra(MOVIE_ID));




        mToolbarInformActivity = (Toolbar) findViewById(R.id.toolbar_inf_act);
        setSupportActionBar(mToolbarInformActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarInformActivity.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setTitle(getIntent().getStringExtra(TITLE));

        mTitleImageView = (ImageView) findViewById(R.id.movie_poster_iv);
        mCastPhoto = (ImageView) findViewById(R.id.cast_photo_iv);
        mCastPhoto1 = (ImageView) findViewById(R.id.cast_photo1_iv);
        mCastPhoto2 = (ImageView) findViewById(R.id.cast_photo2_iv);
        mCastPhoto3 = (ImageView) findViewById(R.id.cast_photo3_iv);
        mCastPhoto4 = (ImageView) findViewById(R.id.cast_photo4_iv);
        mCastPhoto5 = (ImageView) findViewById(R.id.cast_photo5_iv);
        mGenreTextView = (TextView) findViewById(R.id.inform_movie_tv);
        mOverviewTextView = (TextView) findViewById(R.id.overview_tv);
        mNameCast = (TextView) findViewById(R.id.name_cast_tv);
        mNameCast1 = (TextView) findViewById(R.id.name_cast1_tv);
        mNameCast2 = (TextView) findViewById(R.id.name_cast2_tv);
        mNameCast3 = (TextView) findViewById(R.id.name_cast3_tv);
        mNameCast4 = (TextView) findViewById(R.id.name_cast4_tv);
        mNameCast5 = (TextView) findViewById(R.id.name_cast5_tv);
        mDataRealise = (TextView) findViewById(R.id.release_date_tv);
        mCountryName = (TextView) findViewById(R.id.countrу_tv);
        String text = getIntent().getStringExtra(MOVIE_ID);

        Picasso.with(this).load("https://image.tmdb.org/t/p/w533_and_h300_bestv2" + getIntent().getStringExtra(URL_IMAGE)).
                error(R.drawable.ic_no_film).resize(717, 400).into(mTitleImageView);
        mInformActivityPresenter.getMovieInfo(text);
        mInformActivityPresenter.getCastList(text);
    }

    @Override
    public void setCastList(ArrayList<Cast> castList) {

        if (castList.size() >= 6) {


            mNameCast.setText(castList.get(0).getName());
            Picasso.with(this).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + castList.get(0).getProfilePath()).
                    error(R.drawable.ic_user).into(mCastPhoto);
            mNameCast1.setText(castList.get(1).getName());
            Picasso.with(this).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + castList.get(1).getProfilePath()).
                    error(R.drawable.ic_user).into(mCastPhoto1);
            mNameCast2.setText(castList.get(2).getName());
            Picasso.with(this).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + castList.get(2).getProfilePath()).
                    error(R.drawable.ic_user).into(mCastPhoto2);
            mNameCast3.setText(castList.get(3).getName());
            Picasso.with(this).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + castList.get(3).getProfilePath()).
                    error(R.drawable.ic_user).into(mCastPhoto3);
            mNameCast4.setText(castList.get(4).getName());
            Picasso.with(this).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + castList.get(4).getProfilePath()).
                    error(R.drawable.ic_user).into(mCastPhoto4);
            mNameCast5.setText(castList.get(5).getName());
            Picasso.with(this).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + castList.get(5).getProfilePath()).
                    error(R.drawable.ic_user).into(mCastPhoto5);
        }

    }

    @Override
    public void setMovieInfo(MoviesInfo movieInfo) {
        mListMovie = new ArrayList<>();
        mListMovie = movieInfo.getGenres();
        for (Genre genre : mListMovie) {
            mGenreTextView.setText(genre.getName() + " ");
        }
        mOverviewTextView.setText(movieInfo.getOverview());
        mDataRealise.setText(movieInfo.getReleaseDate());
        if (movieInfo.getProductionCountries().size() > 0 && movieInfo.getProductionCountries().get(0).getName() != null) {
            mCountryName.setText(movieInfo.getProductionCountries().get(0).getName());
        }

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setIconForFab(boolean showingFirst) {
        mFloatingActionButton.setImageResource(R.drawable.ic_favorite_white_24dp);
        this.showingFirst = showingFirst;

    }

    @Override
    protected void onStop() {
        super.onStop();
        mInformActivityPresenter.stopFbListener();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mInformActivityPresenter.onDestroy();
//    }
}
