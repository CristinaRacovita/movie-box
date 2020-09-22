package com.example.homework.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.homework.data.MovieGenre;

import java.util.List;

@Dao
public interface MovieGenreDao {
    @Insert
    void insertAll(List<MovieGenre> movieGenres);

    @Query("SELECT id_genre FROM movies INNER JOIN movies_genres ON movies.id_movie = movies_genres.id_movie AND movies.id_movie = :id")
    List<Integer> getGenres(Integer id);

    @Insert
    void insert(MovieGenre movieGenre);
}
