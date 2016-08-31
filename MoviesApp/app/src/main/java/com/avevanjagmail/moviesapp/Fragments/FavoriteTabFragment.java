package com.avevanjagmail.moviesapp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avevanjagmail.moviesapp.ConnectivityReceiver;
import com.avevanjagmail.moviesapp.Interface.MoviesService;
import com.avevanjagmail.moviesapp.Interface.OpenInformActivity;
import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.activities.InformActivity;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavoriteTabFragment extends Fragment implements OpenInformActivity {
    private static final String TAG = FavoriteTabFragment.class.getSimpleName();
    private RecyclerView rv;

    private Movie movie;
    private RvMovieAdapter mMovieAdapter;
    private SharedPreferences sPref;
    private String passedArg1;
    private String passedArg;
    private List<Movie> localList;
    private DbAdapterRv mDbAdapterRv;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserId = mRootRef.child("Users");

    private static final String ARG_SECTION_NUMBER = "section_number";

    public FavoriteTabFragment() {
    }


    public static FavoriteTabFragment newInstance(int sectionNumber) {
        FavoriteTabFragment fragment = new FavoriteTabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        localList = new ArrayList<>();
//        movieArrayList.clear();
        sPref = getActivity().getSharedPreferences("SH", getActivity().MODE_PRIVATE);
        mMovieAdapter = new RvMovieAdapter(this);
        rv.setAdapter(mMovieAdapter);
        passedArg1 = sPref.getString("saved_text", "");
        passedArg = passedArg1.replace(".", "a");
        if(ConnectivityReceiver.isOnline(getActivity().getApplicationContext()) == false){
            localList = Select.from(Movie.class)
                    .where(Condition.prop("properties").eq("Favorite"))
                    .list();

            mDbAdapterRv = new DbAdapterRv();

            mDbAdapterRv.addNewMovies(localList);
            rv.setAdapter(mDbAdapterRv);

        }


        return rootView;
    }

    private Callback<MovieApi> getCallbackFavorite() {
        Log.d(TAG, "getCallbackFavorite");
        return new Callback<MovieApi>() {
            @Override
            public void onResponse(Call<MovieApi> call, Response<MovieApi> response) {
                mMovieAdapter.addNewMovie(response.body());
                movie = new Movie(response.body(), "Favorite");
                movie.save();



            }

            @Override
            public void onFailure(Call<MovieApi> call, Throwable t) {


            }


        };
    }

    @Override
    public void onClickOpen(String id, String url, String title) {
        InformActivity.start(id, url, title, getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        update();

    }

    public void update() {
        mMovieAdapter.clear();
        mUserId.child(passedArg).child("Movies").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                localList = Select.from(Movie.class)
                        .where(Condition.prop("properties").eq("Favorite"))
                        .list();
                for (Movie movie1 : localList) {
                    movie1.delete();

                }
                for (DataSnapshot dataSn : dataSnapshot.getChildren()) {
                    String movie = dataSn.getValue(String.class);
                    MoviesService mService = RetrofitUtil.getMoviesService();
                    Call<MovieApi> requestMovie = mService.getMovieForFavorite(movie, "ru");
                    requestMovie.enqueue(getCallbackFavorite());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                localList = Select.from(Movie.class)
                        .where(Condition.prop("propertis").eq("Favorite"))
                        .list();
                mDbAdapterRv = new DbAdapterRv();
                mDbAdapterRv.addNewMovies(localList);
                rv.setAdapter(mDbAdapterRv);


            }
        });

    }
}

