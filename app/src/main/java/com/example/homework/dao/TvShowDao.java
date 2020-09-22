package com.example.homework.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.homework.data.TvShow;

import java.util.List;

@Dao
public interface TvShowDao {
    @Insert
    void insertAll(List<TvShow> tvShows);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTvShow(TvShow tvShow);

    @Query("SELECT * FROM tvshows")
    List<TvShow> selectAll();

    @Delete
    void deleteTvShow(TvShow tvShow);

    @Query("SELECT * FROM tvshows WHERE favourite = 1")
    List<TvShow> selectFavouriteTvShows();

    @Update
    void updateTvShow(TvShow tvShow);

    @Query("DELETE FROM tvshows WHERE favourite = 0")
    void deleteAll();
}
