package com.avevanjagmail.moviesapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by John on 15.07.2016.
 */
public class Movie extends SugarRecord {
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    public String getBackdropPath() {
        return backdropPath;
    }

    @SerializedName("title")
    @Expose
    private String title;
    public String getTitle() {
        return title;
    }
//    @SerializedName("genre_ids")
//    @Expose
//    private List<Integer> genreIds = new ArrayList<Integer>();
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("id")
    @Expose
    private String MovieId;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

//   // public List<Integer> getGenreIds() {
//        return genreIds;
//    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getMovieId() {
        return MovieId;
    }


    //    @Override
//    public String toString() {
//        return "Movie{" +
//                "backdropPath='" + backdropPath + '\'' +
//                ", title='" + title + '\'' +
//                ", releaseDate='" + releaseDate + '\'' +
//                ", MovieId='" + MovieId + '\'' +
//                ", voteAverage=" + voteAverage +
//                '}';
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Movie movie = (Movie) o;
//
//        if (backdropPath != null ? !backdropPath.equals(movie.backdropPath) : movie.backdropPath != null)
//            return false;
//        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
//        if (releaseDate != null ? !releaseDate.equals(movie.releaseDate) : movie.releaseDate != null)
//            return false;
//        if (MovieId != null ? !MovieId.equals(movie.MovieId) : movie.MovieId != null) return false;
//        return voteAverage != null ? voteAverage.equals(movie.voteAverage) : movie.voteAverage == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = backdropPath != null ? backdropPath.hashCode() : 0;
//        result = 31 * result + (title != null ? title.hashCode() : 0);
//        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
//        result = 31 * result + (MovieId != null ? MovieId.hashCode() : 0);
//        result = 31 * result + (voteAverage != null ? voteAverage.hashCode() : 0);
//        return result;
//    }
}