package com.avevanjagmail.moviesapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avevanjagmail.moviesapp.EndlessRecyclerOnScrollListener;
import com.avevanjagmail.moviesapp.Interface.MoviesService;
import com.avevanjagmail.moviesapp.Interface.OpenInformActivity;
import com.avevanjagmail.moviesapp.Models.ListMovie;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.activities.InformActivity;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by John on 10.07.2016.
 */
public class TopTabFragment extends Fragment implements OpenInformActivity {


    RecyclerView rv;

    private static final String TAG = TopTabFragment.class.getSimpleName();
    LinearLayoutManager llm;
    public RvMovieAdapter mMovieAdapter;

    private final String URL = "http://api.themoviedb.org";
    String key = "a143b2488bf72e7081edb871e0db3a7c";
//    ArrayList<Movie> moviesnew;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    public TopTabFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TopTabFragment newInstance() {
        TopTabFragment fragment = new TopTabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View parentView = inflater.inflate(R.layout.fragment_topmovie, container, false);
        rv = (RecyclerView) parentView.findViewById(R.id.rv);

        llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);


        MoviesService mService = RetrofitUtil.getMoviesService();

        Call<ListMovie> requestMovie = mService.getTopName("ru", 1);

        mMovieAdapter = new RvMovieAdapter(this);
        rv.setAdapter(mMovieAdapter);

        requestMovie.enqueue(getCallback());

        rv.setOnScrollListener(new EndlessRecyclerOnScrollListener(llm) {
            @Override
            public void onLoadMore(int current_page) {
                MoviesService mService = RetrofitUtil.getMoviesService();
                Call<ListMovie> requestMovie = mService.getTopName("ru", current_page);
                requestMovie.enqueue(getCallback());
            }
        });

        return parentView;
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
      InformActivity.start(id, url, title, getContext());

    }
}


/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */