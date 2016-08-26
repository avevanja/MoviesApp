package com.avevanjagmail.moviesapp.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avevanjagmail.moviesapp.Interface.MoviesService;
import com.avevanjagmail.moviesapp.Interface.OpenInformActivity;
import com.avevanjagmail.moviesapp.Models.MovieApi;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.activities.InformActivity;
import com.avevanjagmail.moviesapp.utils.RetrofitUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by John on 10.07.2016.
 */
public class FavoriteTabFragment extends Fragment implements OpenInformActivity {
    private static final String TAG = FavoriteTabFragment.class.getSimpleName();
    RecyclerView rv;
//    ArrayList<Movie> movieArrayList = new ArrayList<>();
    private RvMovieAdapter mMovieAdapter;
    private SharedPreferences sPref;
    private String passedArg1;
    private String passedArg;
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
//        movieArrayList.clear();
        sPref = getActivity().getSharedPreferences("SH", getActivity().MODE_PRIVATE);
        mMovieAdapter = new RvMovieAdapter(this);
        rv.setAdapter(mMovieAdapter);
        passedArg1 = sPref.getString("saved_text", "");
        passedArg = passedArg1.replace(".", "a");
//        mUserId.child(passedArg).child("Movies").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSn : dataSnapshot.getChildren()){
//                    String movie = dataSn.getValue(String.class);
//                    MoviesService mService = RetrofitUtil.getMoviesService();
//                    Call<Movie> requestMovie = mService.getMovieForFavorite(movie, "ru");
//                    requestMovie.enqueue(getCallbackFavorite());
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        return rootView;
    }

    private Callback<MovieApi> getCallbackFavorite() {
        Log.d(TAG, "getCallbackFavorite");
        return new Callback<MovieApi>() {
            @Override
            public void onResponse(Call<MovieApi> call, Response<MovieApi> response) {
//                Log.d(TAG, "obResponse - " + response.body().toString());

                mMovieAdapter.addNewMovie(response.body());
//                movieArrayList.add(response.body());
//                mMovieAdapter.notifyDataSetChanged();
              //  mMovieAdapter.addNewMovies(movieArrayList);

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
                for(DataSnapshot dataSn : dataSnapshot.getChildren()){
                    String movie = dataSn.getValue(String.class);
                    MoviesService mService = RetrofitUtil.getMoviesService();
                    Call<MovieApi> requestMovie = mService.getMovieForFavorite(movie, "ru");
                    requestMovie.enqueue(getCallbackFavorite());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

