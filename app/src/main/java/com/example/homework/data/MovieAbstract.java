package com.example.homework.data;

import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieAbstract implements Serializable {
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("popularity")
    private Double popularity;
    @SerializedName("vote_count")
    private Integer voteCount;
    @SerializedName("video_path")
    private String videoPath;
    @SerializedName("favourite")
    private Boolean favourite;
    @SerializedName("director")
    private String director;
    @Ignore
    @SerializedName("genre_ids")
    private ArrayList<Integer> genreIds;

    @SerializedName("vote_average")
    private Double voteAvg;

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(ArrayList<Integer> genreIds) {
        this.genreIds = genreIds;
    }


    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public MovieAbstract() {
    }

    public MovieAbstract(String posterPath, String overview, String originalLanguage, String backdropPath, Double popularity, Integer voteCount, String videoPath, Boolean favourite, String director, ArrayList<Integer> genreIds, Double voteAvg) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.originalLanguage = originalLanguage;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.videoPath = videoPath;
        this.favourite = favourite;
        this.director = director;
        this.genreIds = genreIds;
        this.voteAvg = voteAvg;
    }

    public MovieAbstract(String posterPath, String overview, String originalLanguage, String backdropPath, Double popularity, Integer voteCount, String videoPath, Boolean favourite, String director, Double voteAvg) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.originalLanguage = originalLanguage;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.videoPath = videoPath;
        this.favourite = favourite;
        this.director = director;
        this.voteAvg = voteAvg;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public Double getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(Double voteAvg) {
        this.voteAvg = voteAvg;
    }

    @Override
    public String toString() {
        return "MovieAbstract{" +
                "posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", popularity=" + popularity +
                ", voteCount=" + voteCount +
                ", videoPath='" + videoPath + '\'' +
                ", favourite=" + favourite +
                ", director='" + director + '\'' +
                ", genreIds=" + genreIds +
                ", voteAvg=" + voteAvg +
                '}';
    }
}
