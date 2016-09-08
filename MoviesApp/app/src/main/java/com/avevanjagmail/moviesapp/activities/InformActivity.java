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

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.models.Cast;
import com.avevanjagmail.moviesapp.models.Genre;
import com.avevanjagmail.moviesapp.models.MoviesInfo;
import com.avevanjagmail.moviesapp.presenter.InformActivityPresenter;
import com.avevanjagmail.moviesapp.view.InformActivityView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class InformActivity extends AppCompatActivity implements InformActivityView {
    private static final String TAG = "InformActivity";
    private TextView TextTet, mOverviewTextView, mNameCast, mNameCast1, mNameCast2, mNameCast3, mNameCast4, mNameCast5, mDataRealise, mCountryName;
    private ImageView TitleImageView, mCastPhoto, mCastPhoto1, mCastPhoto2, mCastPhoto3, mCastPhoto4, mCastPhoto5;
    private Toolbar toolbarInformActivity;
    private ArrayList<Genre> mListMovie;
    private ArrayList<Cast> mListCast;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserId = mRootRef.child("Users");
    private static final String MOVIE_ID = "movie.id";
    private static final String URL_Image = "url";
    private static final String TITLE = "movie_title";
    private boolean showingFirst;
    private InformActivityPresenter mInformActivityPresenter;


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
        mInformActivityPresenter = new InformActivityPresenter();
        mInformActivityPresenter.setInformActivityView(this);




        showingFirst = true;


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (showingFirst) {
                    fab.setImageResource(R.drawable.ic_favorite_white_24dp);
                    mInformActivityPresenter.addFavoriteMovieInRemoteDb(getIntent().getStringExtra(MOVIE_ID));
                    showingFirst = false;

                } else {
                    fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    mInformActivityPresenter.deleteFavoriteMovieFromRemoteDb(getIntent().getStringExtra(MOVIE_ID));
                    showingFirst = true;


                }
            }

        });
        mUserId.child(mInformActivityPresenter.getSPref()).child("Movies").addChildEventListener(new ChildEventListener() {
            @Override


            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String movie = dataSnapshot.getValue(String.class);


                if (movie != null) {
                    if (movie.equals(getIntent().getStringExtra(MOVIE_ID))) {
                        fab.setImageResource(R.drawable.ic_favorite_white_24dp);
                        showingFirst = false;
                    }
                }
                Log.d(TAG, "get map " + movie.toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

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

        TitleImageView = (ImageView) findViewById(R.id.expandedImage);
        mCastPhoto = (ImageView) findViewById(R.id.cast_foto);
        mCastPhoto1 = (ImageView) findViewById(R.id.cast_foto1);
        mCastPhoto2 = (ImageView) findViewById(R.id.cast_foto2);
        mCastPhoto3 = (ImageView) findViewById(R.id.cast_foto3);
        mCastPhoto4 = (ImageView) findViewById(R.id.cast_foto4);
        mCastPhoto5 = (ImageView) findViewById(R.id.cast_foto5);
        TextTet = (TextView) findViewById(R.id.inform_movie_text);
        mOverviewTextView = (TextView) findViewById(R.id.text_overview);
        mNameCast = (TextView) findViewById(R.id.name_cast_text_view);
        mNameCast1 = (TextView) findViewById(R.id.name_cast_text_view1);
        mNameCast2 = (TextView) findViewById(R.id.name_cast_text_view2);
        mNameCast3 = (TextView) findViewById(R.id.name_cast_text_view3);
        mNameCast4 = (TextView) findViewById(R.id.name_cast_text_view4);
        mNameCast5 = (TextView) findViewById(R.id.name_cast_text_view5);
        mDataRealise = (TextView) findViewById(R.id.release_date_text_view);
        mCountryName = (TextView) findViewById(R.id.countrу_text_view);
        String text = getIntent().getStringExtra(MOVIE_ID);

        Picasso.with(this).load("https://image.tmdb.org/t/p/w533_and_h300_bestv2" + getIntent().getStringExtra(URL_Image)).
                error(R.drawable.nofim).resize(717, 400).into(TitleImageView);
        mInformActivityPresenter.getMovieInfo(text);
        mInformActivityPresenter.getCastList(text);
    }

    @Override
    public void setCastList(ArrayList<Cast> castList) {
        mListCast = castList;
        if (mListCast.size() >= 6) {


            mNameCast.setText(mListCast.get(0).getName());
            Picasso.with(mCastPhoto.getContext()).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + mListCast.get(0).getProfilePath()).
                    error(R.drawable.user_icon).into(mCastPhoto);
            mNameCast1.setText(mListCast.get(1).getName());
            Picasso.with(mCastPhoto.getContext()).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + mListCast.get(1).getProfilePath()).
                    error(R.drawable.user_icon).into(mCastPhoto1);
            mNameCast2.setText(mListCast.get(2).getName());
            Picasso.with(mCastPhoto.getContext()).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + mListCast.get(2).getProfilePath()).
                    error(R.drawable.user_icon).into(mCastPhoto2);
            mNameCast3.setText(mListCast.get(3).getName());
            Picasso.with(mCastPhoto.getContext()).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + mListCast.get(3).getProfilePath()).
                    error(R.drawable.user_icon).into(mCastPhoto3);
            mNameCast4.setText(mListCast.get(4).getName());
            Picasso.with(mCastPhoto.getContext()).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + mListCast.get(4).getProfilePath()).
                    error(R.drawable.user_icon).into(mCastPhoto4);
            mNameCast5.setText(mListCast.get(5).getName());
            Picasso.with(mCastPhoto.getContext()).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + mListCast.get(5).getProfilePath()).
                    error(R.drawable.user_icon).into(mCastPhoto5);
        }

    }

    @Override
    public void setMovieInfo(MoviesInfo movieInfo) {
        mListMovie = movieInfo.getGenres();
        for (Genre genre : mListMovie) {
            TextTet.setText(genre.getName() + " ");
        }
        mOverviewTextView.setText(movieInfo.getOverview());
        mDataRealise.setText(movieInfo.getReleaseDate());
        if (movieInfo.getProductionCountries().size()>0 && movieInfo.getProductionCountries().get(0).getName() != null ) {
            mCountryName.setText(movieInfo.getProductionCountries().get(0).getName());
        }

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }


}
