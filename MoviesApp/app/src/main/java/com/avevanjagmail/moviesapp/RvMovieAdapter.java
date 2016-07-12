package com.avevanjagmail.moviesapp;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by John on 12.07.2016.
 */
public class RvMovieAdapter extends RecyclerView.Adapter<RvMovieAdapter.MovieViewHolder> {
    List<Movie> movies;
    RvMovieAdapter(List<Movie> movies){
        this.movies = movies;
    }
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_movie, parent, false);
        MovieViewHolder mvh = new MovieViewHolder(v);
        return mvh;

    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.setName(movies.get(position).getName());
        holder.setImage(movies.get(position).getPosterId());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView ivPoster;
        TextView tvNameMovie;
        private final View parentView;


        public MovieViewHolder (View parentView) {
            super(parentView);

            this.parentView = parentView;
            cv = (CardView)itemView.findViewById(R.id.movie_card_view);
            ivPoster = (ImageView) itemView.findViewById(R.id.movie_poster_image_view);
            tvNameMovie = (TextView) itemView.findViewById(R.id.name_movi_tv);
        }
        public void setName(String name){
            tvNameMovie.setText(name);
        }


        public void setImage(int resource) {
            ivPoster.setImageDrawable(ContextCompat.getDrawable(parentView.getContext(), resource));
        }
    }
}
