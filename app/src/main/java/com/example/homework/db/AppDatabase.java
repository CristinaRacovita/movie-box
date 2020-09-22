package com.example.homework.db;

import androidx.room.RoomDatabase;

import com.example.homework.dao.GenreDao;
import com.example.homework.dao.MovieDao;
import com.example.homework.dao.MovieGenreDao;
import com.example.homework.dao.TvShowDao;
import com.example.homework.dao.TvShowGenreDao;
import com.example.homework.dao.UserDao;
import com.example.homework.data.Genre;
import com.example.homework.data.Movie;
import com.example.homework.data.MovieGenre;
import com.example.homework.data.Review;
import com.example.homework.data.TvShow;
import com.example.homework.data.TvShowGenre;
import com.example.homework.data.User;

@androidx.room.Database(version = 1, entities = {Movie.class, TvShow.class, Genre.class, Review.class, MovieGenre.class, TvShowGenre.class, User.class}, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();

    public abstract GenreDao genreDao();

    public abstract MovieGenreDao movieGenreDao();

    public abstract TvShowDao tvShowDao();

    public abstract TvShowGenreDao tvShowGenreDao();

    public abstract UserDao userDao();
}
