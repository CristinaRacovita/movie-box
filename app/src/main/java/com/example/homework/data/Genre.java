package com.example.homework.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "genres")
public class Genre {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_genre")
    @SerializedName("id")
    private Integer genreId;
    @SerializedName("name")
    @ColumnInfo(name = "name_genre")
    private String genreTitle;

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getGenreTitle() {
        return genreTitle;
    }

    public void setGenreTitle(String genreTitle) {
        this.genreTitle = genreTitle;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreId=" + genreId +
                ", genreTitle='" + genreTitle + '\'' +
                '}';
    }
}
