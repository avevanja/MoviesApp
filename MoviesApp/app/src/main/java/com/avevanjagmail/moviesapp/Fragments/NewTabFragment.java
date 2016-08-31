package com.avevanjagmail.moviesapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.EndlessRecyclerOnScrollListener;
import com.avevanjagmail.moviesapp.Interface.MoviesService;
import com.avevanjagmail.moviesapp.Interface.OpenInformActivity;
import com.avevanjagmail.moviesapp.models.ListMovie;
import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.activities.InformActivity;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by John on 10.07.2016.
 */
public class NewTabFragment extends Fragment implements OpenInformActivity {
    private RecyclerView rv;
    private static final String TAG = "bla";
    private LinearLayoutManager llm;
    private List<Movie> localList;
    private RvMovieAdapter mMovieAdapter;
    private Movie movie;
    private DbAdapterRv mDbAdapterRv;
    public NewTabFragment() {
    }

    public static NewTabFragment newInstance() {
        NewTabFragment fragment = new NewTabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_main, container, false);

        rv = (RecyclerView) parentView.findViewById(R.id.rv);

        llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);


        MoviesService mService = RetrofitUtil.getMoviesService();

        Call<ListMovie> requestMovie = mService.getNewMovie("ru", 1);

        mMovieAdapter = new RvMovieAdapter(this);
        rv.setAdapter(mMovieAdapter);

        requestMovie.enqueue(getCallback());

        rv.setOnScrollListener(new EndlessRecyclerOnScrollListener(llm) {
            @Override
            public void onLoadMore(int current_page) {
                MoviesService mService = RetrofitUtil.getMoviesService();
                Call<ListMovie> requestMovie = mService.getNewMovie("ru", current_page);
                requestMovie.enqueue(getCallbackLoadMore());
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

                localList = Select.from(Movie.class)
                        .where(Condition.prop("properties").eq("New"))
                        .list();
                for (Movie movie1 : localList) {
                    movie1.delete();

                }

                for (MovieApi movieApi : response.body().getResults()) {
                    movie = new Movie(movieApi, "New");
                    movie.save();
                }

            }

            @Override
            public void onFailure(Call<ListMovie> call, Throwable t) {
                Log.e(TAG, "Eror" + t.getMessage());
                t.printStackTrace();

                localList = Select.from(Movie.class)
                        .where(Condition.prop("properties").eq("New"))
                        .list();

//                localList = Movie.listAll(Movie.class);

                mDbAdapterRv = new DbAdapterRv();
                mDbAdapterRv.addNewMovies(localList);
                rv.setAdapter(mDbAdapterRv);
                Toast.makeText(getActivity().getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();

            }
        };
    }
    private Callback<ListMovie> getCallbackLoadMore() {
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

                Toast.makeText(getActivity().getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();

            }
        };
    }

    @Override
    public void onClickOpen(String id, String url, String title) {
        InformActivity.start(id, url, title, getContext());
    }
}

