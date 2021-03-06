package com.avevanjagmail.moviesapp.presenter;


import android.util.Log;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.models.CastList;
import com.avevanjagmail.moviesapp.models.MoviesInfo;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.avevanjagmail.moviesapp.utils.SharedPreferencesUtility;
import com.avevanjagmail.moviesapp.view.InformActivityView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InformActivityPresenter {
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserId = mRootRef.child("Users");
    private String passedArg;
    private boolean showingFirst;
    private ChildEventListener eventListener;
    private SharedPreferencesUtility mSharedPreferencesUtility = new SharedPreferencesUtility();
    private static final String SHARED = "emailOrId";
    private static final String TAG = InformActivityPresenter.class.getSimpleName();
    private InformActivityView mInformActivityView;

    public void setInformActivityView(InformActivityView mInformActivityView) {
        this.mInformActivityView = mInformActivityView;
    }

    public void getMovieInfo(String text) {
        RetrofitUtil.getMoviesService()
                .getMovieInfoFromId(text, mInformActivityView.getContext().getString(R.string.query_lng))
                .enqueue(getCallback());
    }

    public void getCastList(String text) {
        RetrofitUtil.getMoviesService()
                .getCastList(text)
                .enqueue(getCastListCallback());
    }

    private Callback<MoviesInfo> getCallback() {

        return new Callback<MoviesInfo>() {
            @Override
            public void onResponse(Call<MoviesInfo> call, Response<MoviesInfo> response) {
                if (response.body() != null) {
                    mInformActivityView.setMovieInfo(response.body());
                }
            }

            @Override
            public void onFailure(Call<MoviesInfo> call, Throwable t) {

                t.printStackTrace();
            }
        };
    }

    private Callback<CastList> getCastListCallback() {
        return new Callback<CastList>() {
            @Override
            public void onResponse(Call<CastList> call, Response<CastList> response) {
                if (response.body() != null) {
                    mInformActivityView.setCastList(response.body().getCast());
                } else {
                    Log.e(TAG, "onResponse: getCastList is null");
                }

            }

            @Override
            public void onFailure(Call<CastList> call, Throwable t) {

                t.printStackTrace();

            }
        };
    }

    public void addFavoriteMovieInRemoteDb(String movieId) {
        mUserId.child(getNameChild()).child("Movies").child(movieId).setValue(movieId);
    }

    public void deleteFavoriteMovieFromRemoteDb(String movieId) {
        mUserId.child(getNameChild()).child("Movies").child(movieId).removeValue();

    }

    public String getNameChild() {
        passedArg = mSharedPreferencesUtility.getSPref(mInformActivityView.getContext(), SHARED).replace(".", "a");
        return passedArg;
    }
    public void checkMovieForFavorite (final String movieId){

        eventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String movie = dataSnapshot.getValue(String.class);

                if (movie != null) {
                    if (movie.equals(movieId)) {
                        showingFirst = false;
                        mInformActivityView.setIconForFab(showingFirst);
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
        };
        mUserId.child(getNameChild()).child("Movies").addChildEventListener(eventListener);
    }
    public void stopFbListener (){
        mUserId.child(getNameChild()).child("Movies").removeEventListener(eventListener);
    }
    public void onDestroy(){
        mInformActivityView = null;
    }

}
