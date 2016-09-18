package com.avevanjagmail.moviesapp.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.avevanjagmail.moviesapp.utils.ConnectivityUtility;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.managers.DBManager;
import com.avevanjagmail.moviesapp.models.Movie;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.avevanjagmail.moviesapp.view.FavoriteFragmentView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragmentPresenter {
    private static final String TAG = FavoriteFragmentPresenter.class.getSimpleName();
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserId = mRootRef.child("Users");
    private SharedPreferences sPref;
    private String passedArg1;
    private String passedArg;
    private List<Movie> localList = new ArrayList<>();
    private Movie movie;
    private FavoriteFragmentView mFavoriteFragmentView;
    private ArrayList<String> listId = new ArrayList<>();
    private static final String SHARED = "emailOrId";

    public void setFavoriteFragmentView(FavoriteFragmentView mFavoriteFragmentView) {
        this.mFavoriteFragmentView = mFavoriteFragmentView;
    }

    public void UpdateRemoteDb() {
        sPref = mFavoriteFragmentView.getContext().getSharedPreferences("SH", mFavoriteFragmentView.getContext().MODE_PRIVATE);
        passedArg1 = sPref.getString(SHARED, "");
        passedArg = passedArg1.replace(".", "a");
        if (!ConnectivityUtility.isOnline(mFavoriteFragmentView.getContext())) {
            localList = DBManager.getLocalListMovie("Favorite");
            mFavoriteFragmentView.setLocalFavoriteMovies((ArrayList<Movie>) localList);
        }

        mUserId.child(passedArg).child("Movies").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                localList = DBManager.getLocalListMovie("Favorite");
                for (Movie movie1 : localList) {
                    movie1.delete();

                }
                for (DataSnapshot dataSn : dataSnapshot.getChildren()) {
                    String movie = dataSn.getValue(String.class);
                    Log.d(TAG, "onDataChange: movieId is " + movie);

                    listId.add(movie);
                    RetrofitUtil.getMoviesService()
                            .getMovieForFavorite(movie, mFavoriteFragmentView.getContext().getString(R.string.query_lng))
                            .enqueue(getCallbackFavorite());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private Callback<MovieApi> getCallbackFavorite() {
        return new Callback<MovieApi>() {
            @Override
            public void onResponse(Call<MovieApi> call, Response<MovieApi> response) {
                if (response.body() != null) {
                    Log.d(TAG, "onResponse: movies received " + response.body().toString());
                    mFavoriteFragmentView.setFavoriteMovies(response.body());
                    movie = new Movie(response.body(), "Favorite");
                    movie.save();

                } else {
                    Log.e(TAG, "onResponse: body is null");
                }
            }

            @Override
            public void onFailure(Call<MovieApi> call, Throwable t) {
                t.printStackTrace();


            }


        };
    }

    public void onStop() {

    }
}

