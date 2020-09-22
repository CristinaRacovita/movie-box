package com.example.homework.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.homework.data.MovieGenre;
import com.example.homework.data.TvShowGenre;

import java.util.List;

@Dao
public interface TvShowGenreDao {
    @Insert
    void insertAll(List<TvShowGenre> tvShowGenres);

    @Query("SELECT id_genre FROM tvshows INNER JOIN tv_show_genres ON tv_show_genres.id_tv_show = tvshows.id_tv_show AND tvshows.title = :title")
    List<Integer> getGenres(String title);

    @Insert
    void insert(MovieGenre movieGenre);
}
