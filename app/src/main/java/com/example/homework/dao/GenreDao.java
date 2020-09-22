package com.example.homework.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.homework.data.Genre;

import java.util.List;

@Dao
public interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Genre> genres);

    @Query("SELECT name_genre FROM genres WHERE id_genre = :id")
    String selectGenre(Integer id);

    @Query("SELECT id_genre FROM genres WHERE name_genre = :name")
    Integer selectGenre(String name);

    @Insert
    void insert(Genre genre);

}
