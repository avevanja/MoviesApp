package com.avevanjagmail.moviesapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class LocalDbRecyclerAdapter extends RecyclerView.Adapter<LocalDbRecyclerAdapter.MovieViewHolder> {
    private static final String TAG = MovieRecyclerAdapter.class.getSimpleName();


    private List<Movie> mMovies = new ArrayList<>();

    public LocalDbRecyclerAdapter() {
    }


    public void addNewMovies(List<Movie> newMoviesList) {

        mMovies.addAll(newMoviesList);
        notifyDataSetChanged();
    }

    public void clear() {
        mMovies.clear();
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
        final Context context = holder.mImagePosterMovie.getContext();
        Picasso.with(context).load("https://image.tmdb.org/t/p/w533_and_h300_bestv2" + mMovies.get(position).getBackdropPath()).
                error(R.drawable.nofim).resize(717, 400).into(holder.mImagePosterMovie);
        holder.setDataMovieTextView(mMovies.get(position).getReleaseDate());
        holder.setTopMark(mMovies.get(position).getVoteAverage());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, R.string.error_no_internet, Toast.LENGTH_SHORT).show();


            }
        });


    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private ImageView mImagePosterMovie;
        private TextView mNameMovieTextView;
        private TextView mDataMovieTextView;
        private TextView mMarkTextView;
        private View parentView;


        public MovieViewHolder(View parentView) {
            super(parentView);
            this.parentView = parentView;
            mCardView = (CardView) itemView.findViewById(R.id.movie_card_view);
            mImagePosterMovie = (ImageView) itemView.findViewById(R.id.movie_poster_image_view);

            mNameMovieTextView = (TextView) itemView.findViewById(R.id.name_movi_tv);
            mDataMovieTextView = (TextView) itemView.findViewById(R.id.ganre_text);
            mMarkTextView = (TextView) itemView.findViewById(R.id.top_text);
        }

        public void setName(String name) {
            mNameMovieTextView.setText(name);
        }

        public void setDataMovieTextView(String dataRealiseMovie) {
            mDataMovieTextView.setText(dataRealiseMovie);
        }

        public void setTopMark(Double mark) {
            mMarkTextView.setText(mark.toString());
        }


    }

}
