package com.avevanjagmail.moviesapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avevanjagmail.moviesapp.Models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by John on 12.07.2016.
 */
public class RvMovieAdapter extends RecyclerView.Adapter<RvMovieAdapter.MovieViewHolder> {
    List<Movie> movies;

    RvMovieAdapter(List<Movie> movies) {
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

        holder.setName(movies.get(position).getTitle());
        Context context = holder.ivPoster.getContext();
        Picasso.with(context).load("https://image.tmdb.org/t/p/w533_and_h300_bestv2" + movies.get(position).getBackdropPath()).
                error(R.drawable.ava).resize(717,400).into(holder.ivPoster);
        holder.setDataMovie(movies.get(position).getReleaseDate());
        holder.setTopMark(movies.get(position).getVoteAverage());
    }




    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView ivPoster;
        TextView tvNameMovie;
        TextView dataMovie;
        TextView topText;

        private final View parentView;


        public MovieViewHolder(View parentView) {
            super(parentView);

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

