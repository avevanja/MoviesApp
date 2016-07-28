package com.avevanjagmail.moviesapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avevanjagmail.moviesapp.Interface.MoviesServise;
import com.avevanjagmail.moviesapp.Models.ListMovie;
import com.avevanjagmail.moviesapp.Models.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by John on 10.07.2016.
 */
public class TopTabFragment extends Fragment {

    RecyclerView rv;
    private static final String TAG = "bla" ;

    private final String URL = "http://api.themoviedb.org";
    String key = "a143b2488bf72e7081edb871e0db3a7c";
    ArrayList<Movie> moviesnew;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public TopTabFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TopTabFragment newInstance(int sectionNumber) {
        TopTabFragment fragment = new TopTabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View parentView = inflater.inflate(R.layout.fragment_topmovie, container, false);
        rv = (RecyclerView) parentView.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);


        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(URL)
                .build();
        MoviesServise mService = retrofit.create(MoviesServise.class);
        Call<ListMovie> requestMovie = mService.getTopName("ru");

        requestMovie.enqueue(new Callback<ListMovie>() {
            @Override
            public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {


                ListMovie listmovies = response.body();
                moviesnew = new ArrayList<Movie>(listmovies.getResults());
                rv.setAdapter(new RvMovieAdapter(moviesnew));


            }

            @Override
            public void onFailure(Call<ListMovie> call, Throwable t) {
                Log.e(TAG, "Eror" + t.getMessage());

            }
        });
        return parentView;
    }
}

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */