package com.avevanjagmail.moviesapp.Fragments;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avevanjagmail.moviesapp.Interface.OpenInformActivity;
import com.avevanjagmail.moviesapp.Models.Movie;
import com.avevanjagmail.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by John on 12.07.2016.
 */
public class RvMovieAdapter extends RecyclerView.Adapter<RvMovieAdapter.MovieViewHolder> {
    private static final String TAG = RvMovieAdapter.class.getSimpleName();
    private OpenInformActivity mCallback;


    private List<Movie> mMovies = new ArrayList<>();


    public RvMovieAdapter() {
    }

    public RvMovieAdapter(List<Movie> mMovies ) {
        this.mMovies = mMovies;

    }

    public RvMovieAdapter(OpenInformActivity call) {
        this.mCallback = call;
    }
    public RvMovieAdapter(OpenInformActivity call, List<Movie> mMovies) {
        this.mCallback = call;
        this.mMovies = mMovies;
    }

    public void addNewMovies(List<Movie> newMoviesList) {

        mMovies.addAll(newMoviesList);
        notifyDataSetChanged();
    }

    public void clear() {
        mMovies.clear();
        notifyDataSetChanged();
    }

    public void addNewMovie(Movie movie) {
//        Log.d(TAG, "addNewMovie " + movie.toString());
        HashSet<Movie> movies = new HashSet<>(mMovies);
        movies.add(movie);
        mMovies.clear();
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_movie, parent, false);
        MovieViewHolder mvh = new MovieViewHolder(v);

        return mvh;

    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {

        holder.setName(mMovies.get(position).getTitle());
        Context context = holder.ivPoster.getContext();
        Picasso.with(context).load("https://image.tmdb.org/t/p/w533_and_h300_bestv2" + mMovies.get(position).getBackdropPath()).
                error(R.drawable.ava).resize(717, 400).into(holder.ivPoster);
        holder.setDataMovie(mMovies.get(position).getReleaseDate());
        holder.setTopMark(mMovies.get(position).getVoteAverage());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                InformActivity.start(mMovies.get(position).getId(), holder.cv.getContext());
//                Intent intent = new Intent(holder.cv.getContext(), InformActivity.class);
//                intent.putExtra("id",mMovies.get(position).getId() );
//                holder.cv.getContext().startActivity(intent);
                mCallback.onClickOpen(mMovies.get(position).getId(), mMovies.get(position).getBackdropPath(), mMovies.get(position).getTitle());


            }
        });


    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView ivPoster;
        TextView tvNameMovie;
        TextView dataMovie;
        TextView topText;


        private final View parentView;


        public MovieViewHolder(final View parentView) {
            super(parentView);
//            parentView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(parentView.getContext(), InformActivity.class);
//                    intent.putExtra("id", topText.getText());
//                    parentView.getContext().startActivity(intent);
//
//
//                }
//            });

            this.parentView = parentView;
            cv = (CardView) itemView.findViewById(R.id.movie_card_view);
            ivPoster = (ImageView) itemView.findViewById(R.id.movie_poster_image_view);

            tvNameMovie = (TextView) itemView.findViewById(R.id.name_movi_tv);
            dataMovie = (TextView) itemView.findViewById(R.id.ganre_text);
            topText = (TextView) itemView.findViewById(R.id.top_text);
        }

        public void setName(String name) {
            tvNameMovie.setText(name);
        }

        public void setDataMovie(String datamovie) {
            dataMovie.setText(datamovie);
        }

        public void setTopMark(Double mark) {
            topText.setText(mark.toString());
        }


    }

}

