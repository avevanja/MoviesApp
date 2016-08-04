package com.avevanjagmail.moviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.avevanjagmail.moviesapp.R;

/**
 * Created by paulg on 03.08.2016.
 */
public class InformActivity extends AppCompatActivity {
    TextView TextTet;


    private static final String MOVIE_ID = "movie.id";

    public static void start(String movieId, Context context) {
        Intent starter = new Intent(context, InformActivity.class);
        starter.putExtra(MOVIE_ID, movieId);
        context.startActivity(starter);
    }

    TextView TextTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);
        TextTet = (TextView)findViewById(R.id.id_view);
        String text = getIntent().getStringExtra(MOVIE_ID);
        TextTet.setText(text.toString());
    }
}
