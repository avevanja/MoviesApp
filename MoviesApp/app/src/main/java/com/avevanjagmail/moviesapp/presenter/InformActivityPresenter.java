package com.avevanjagmail.moviesapp.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.avevanjagmail.moviesapp.models.CastList;
import com.avevanjagmail.moviesapp.models.MoviesInfo;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.avevanjagmail.moviesapp.view.InformActivityView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InformActivityPresenter {
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserId = mRootRef.child("Users");
    private SharedPreferences sPref;
    private String passedArg1;
    private String passedArg;
    private static final String SHARED = "emailOrId";

    private static final String TAG = InformActivityPresenter.class.getSimpleName();

    private InformActivityView mInformActivityView;

    public void setInformActivityView(InformActivityView mInformActivityView) {
        this.mInformActivityView = mInformActivityView;
    }

    public void getMovieInfo(String text) {
        RetrofitUtil.getMoviesService()
                .getMovieInfoFromId(text, "ru")
                .enqueue(getCallback());
//        MoviesService mService = RetrofitUtil.getMoviesService();
//        Call<MoviesInfo> requestMovie = mService.getMovieInfoFromId(text, "ru");
//        requestMovie.enqueue(getCallback());
    }

    public void getCastList(String text) {
        RetrofitUtil.getMoviesService()
                .getCastList(text)
                .enqueue(getCastListCallback());
//        MoviesService mServiceCastList = RetrofitUtil.getMoviesService();
//        Call<CastList> requestCastList = mServiceCastList.getCastList(text);
//        requestCastList.enqueue(getCastListCallback());
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
        mUserId.child(getSPref()).child("Movies").child(movieId).setValue(movieId);
    }

    public void deleteFavoriteMovieFromRemoteDb(String movieId) {
        mUserId.child(getSPref()).child("Movies").child(movieId).removeValue();

    }

    public String getSPref() {
        sPref = mInformActivityView.getContext().getSharedPreferences("SH", mInformActivityView.getContext().MODE_PRIVATE);
        passedArg1 = sPref.getString(SHARED, "");
        passedArg = passedArg1.replace(".", "a");
        return passedArg;
    }

}
