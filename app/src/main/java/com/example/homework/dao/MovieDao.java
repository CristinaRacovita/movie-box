package com.example.homework.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.homework.data.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    List<Movie> getAllMovies();

    @Insert
    void insertAll(List<Movie> movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM movies WHERE favourite = 1")
    List<Movie> selectFavouriteMovies();

    @Update
    void updateMovie(Movie movie);

    @Query("DELETE FROM movies WHERE favourite = 0")
    void deleteAll();
}
