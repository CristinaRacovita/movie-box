package com.example.homework.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "tvshows")
public class TvShow extends MovieAbstract {
    @PrimaryKey
    @ColumnInfo(name = "id_tv_show")
    @SerializedName("id")
    private Integer tvShowId;
    @SerializedName("first_air_date")
    private String firstAir;
    @Ignore
    @SerializedName("origin_country")
    private ArrayList<String> originCountry;
    @SerializedName("name")
    private String title;
    @SerializedName("original_name")
    private String originalTitle;

    public TvShow(String posterPath, String overview, String originalLanguage, String backdropPath, Double popularity, Integer voteCount, String videoPath, Boolean favourite, String director, ArrayList<Integer> genreIds, Integer tvShowId, String firstAir, ArrayList<String> originCountry, String title, String originalTitle, Double voteAvg) {
        super(posterPath, overview, originalLanguage, backdropPath, popularity, voteCount, videoPath, favourite, director, genreIds, voteAvg);
        this.tvShowId = tvShowId;
        this.firstAir = firstAir;
        this.originCountry = originCountry;
        this.title = title;
        this.originalTitle = originalTitle;
    }

    public TvShow(String posterPath, String overview, String originalLanguage, String backdropPath, Double popularity, Integer voteCount, String videoPath, Boolean favourite, String director, Integer tvShowId, String firstAir, String title, String originalTitle, Double voteAvg) {
        super(posterPath, overview, originalLanguage, backdropPath, popularity, voteCount, videoPath, favourite, director, voteAvg);
        this.tvShowId = tvShowId;
        this.firstAir = firstAir;
        this.title = title;
        this.originalTitle = originalTitle;
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

    public Integer getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(Integer tvShowId) {
        this.tvShowId = tvShowId;
    }

    public String getFirstAir() {
        return firstAir;
    }

    public void setFirstAir(String firstAir) {
        this.firstAir = firstAir;
    }

    public ArrayList<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(ArrayList<String> originCountry) {
        this.originCountry = originCountry;
    }

    @Override
    public String toString() {
        return super.toString() + "TvShow{" +
                "tvShowId=" + tvShowId +
                ", firstAir='" + firstAir + '\'' +
                ", originCountry='" + originCountry + '\'' +
                '}';
    }
}
