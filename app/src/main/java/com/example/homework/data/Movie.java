package com.example.homework.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "movies")
public class Movie extends MovieAbstract implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_movie")
    @SerializedName("id")
    private Integer movieId;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("title")
    private String title;
    @SerializedName("original_title")
    private String originalTitle;

    public Movie() {
    }


    public Movie(String posterPath, String overview, String originalLanguage, String backdropPath, Double popularity, Integer voteCount, String videoPath, Boolean favourite, String director, ArrayList<Integer> genreIds, Integer movieId, Double voteAvg, String releaseDate, String title, String originalTitle) {
        super(posterPath, overview, originalLanguage, backdropPath, popularity, voteCount, videoPath, favourite, director, genreIds, voteAvg);
        this.movieId = movieId;
        this.releaseDate = releaseDate;
        this.title = title;
        this.originalTitle = originalTitle;
    }

    public Movie(String posterPath, String overview, String originalLanguage, String backdropPath, Double popularity, Integer voteCount, String videoPath, Boolean favourite, String director, Integer movieId, Double voteAvg, String releaseDate, String title, String originalTitle) {
        super(posterPath, overview, originalLanguage, backdropPath, popularity, voteCount, videoPath, favourite, director, voteAvg);
        this.movieId = movieId;
        this.releaseDate = releaseDate;
        this.title = title;
        this.originalTitle = originalTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return super.toString() + "Movie{" +
                "movieId=" + movieId +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }
}
