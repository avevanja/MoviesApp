package com.avevanjagmail.moviesapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avevanjagmail.moviesapp.interfaces.OpenInformActivity;
import com.avevanjagmail.moviesapp.models.MovieApi;
import com.avevanjagmail.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by John on 12.07.2016.
 */
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {
    private static final String TAG = MovieRecyclerAdapter.class.getSimpleName();
    private OpenInformActivity mCallback;


    private List<MovieApi> mMovies = new ArrayList<>();


    public MovieRecyclerAdapter(OpenInformActivity call) {
        this.mCallback = call;
    }


    public void addNewMovies(List<MovieApi> newMoviesList) {

        mMovies.addAll(newMoviesList);
        notifyDataSetChanged();
    }

    public void clear() {
        mMovies.clear();
//        notifyDataSetChanged();
    }

    public void addNewMovie(MovieApi movie) {
        HashSet<MovieApi> movies = new HashSet<>(mMovies);
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
        Context context = holder.mPosterImageView.getContext();
        Picasso.with(context).load("https://image.tmdb.org/t/p/w533_and_h300_bestv2" + mMovies.get(position).getBackdropPath()).
                error(R.drawable.nofim).resize(717, 400).into(holder.mPosterImageView);
        holder.setDataMovieTextView(mMovies.get(position).getReleaseDate());
        holder.setTopMark(mMovies.get(position).getVoteAverage());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onClickOpen(mMovies.get(position).getId(), mMovies.get(position).getBackdropPath(), mMovies.get(position).getTitle());


            }
        });


    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView mPosterImageView;
        TextView mNameMovieTextView;
        TextView mDataMovieTextView;
        TextView mMarkMovieTextView;


        private final View parentView;


        public MovieViewHolder(final View parentView) {
            super(parentView);

            this.parentView = parentView;
            mCardView = (CardView) itemView.findViewById(R.id.movie_card_view);
            mPosterImageView = (ImageView) itemView.findViewById(R.id.movie_poster_image_view);
            mNameMovieTextView = (TextView) itemView.findViewById(R.id.name_movi_tv);
            mDataMovieTextView = (TextView) itemView.findViewById(R.id.ganre_text);
            mMarkMovieTextView = (TextView) itemView.findViewById(R.id.top_text);
        }

        public void setName(String name) {
            mNameMovieTextView.setText(name);
        }

        public void setDataMovieTextView(String dataRealiseMovie) {
            mDataMovieTextView.setText(dataRealiseMovie);
        }

        public void setTopMark(Double mark) {
            mMarkMovieTextView.setText(mark.toString());
        }


    }

}

