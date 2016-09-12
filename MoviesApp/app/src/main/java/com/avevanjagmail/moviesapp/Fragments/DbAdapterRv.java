package com.avevanjagmail.moviesapp.fragments;

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

/**
 * Created by paulg on 29.08.2016.
 */
public class DbAdapterRv extends RecyclerView.Adapter<DbAdapterRv.MovieViewHolder> {
    private static final String TAG = RvMovieAdapter.class.getSimpleName();


    private List<Movie> mMovies = new ArrayList<>();

    public DbAdapterRv() {
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
        final Context context = holder.ivPoster.getContext();
        Picasso.with(context).load("https://image.tmdb.org/t/p/w533_and_h300_bestv2" + mMovies.get(position).getBackdropPath()).
                error(R.drawable.nofim).resize(717, 400).into(holder.ivPoster);
        holder.setDataMovie(mMovies.get(position).getReleaseDate());
        holder.setTopMark(mMovies.get(position).getVoteAverage());
        holder.cv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();


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


        private View parentView;


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
