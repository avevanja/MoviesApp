package com.avevanjagmail.moviesapp.presenter;

import android.util.Log;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.models.ListMovie;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.avevanjagmail.moviesapp.view.SearchActivityView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivityPresenter {
    private static final String TAG = SearchActivityPresenter.class.getSimpleName() ;
    private SearchActivityView mSearchActivityView;
    public void setSearchActivityView (SearchActivityView searchActivityView){
        this.mSearchActivityView = searchActivityView;
    }

    public void loadSearchMovies (String query)

    {
        RetrofitUtil.getMoviesService()
                .getSearchMovies(query)
                .enqueue(getCallback());
    }
    private Callback<ListMovie> getCallback() {
        Log.d(TAG, "getCallback");
        return new Callback<ListMovie>() {
            @Override
            public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
                Log.d(TAG, "getCallback onResponse");
                if(response.body().getResults().size() == 0){
                    Toast.makeText(mSearchActivityView.getContext(), R.string.error_search, Toast.LENGTH_SHORT).show();
                }
                mSearchActivityView.setSearchMoviesList(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ListMovie> call, Throwable t) {
                Log.e(TAG, "Error" + t.getMessage());
                t.printStackTrace();
            }
        };
    }


}
