package com.avevanjagmail.moviesapp.presenter;

import android.util.Log;

import com.avevanjagmail.moviesapp.interfaces.MoviesService;
import com.avevanjagmail.moviesapp.models.CastList;
import com.avevanjagmail.moviesapp.models.MoviesInfo;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.avevanjagmail.moviesapp.view.InformActivityView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by paulg on 06.09.2016.
 *
 */
public class InformActivityPresenter {

    private static final String TAG = InformActivityPresenter.class.getSimpleName();

    public void setInformActivityView(InformActivityView mInformActivityView){
        this.mInformActivityView = mInformActivityView;
    }
    private InformActivityView mInformActivityView;
    public void getMovieInfo(String text) {
        MoviesService mService = RetrofitUtil.getMoviesService();
        Call<MoviesInfo> requestMovie = mService.getMovieInfoFromId(text, "ru");
        requestMovie.enqueue(getCallback());
    }
    public void getCastList(String text){
        MoviesService mServiceCastList = RetrofitUtil.getMoviesService();
        Call<CastList> requestCastList = mServiceCastList.getCastList(text);
        requestCastList.enqueue(getCastListCallback());
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
}
