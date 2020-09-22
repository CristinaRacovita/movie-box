package com.example.homework.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies_genres",
        foreignKeys = {@ForeignKey(entity = Movie.class,
                parentColumns = "id_movie",
                childColumns = "id_movie",
                onDelete = ForeignKey.CASCADE),

                @ForeignKey(entity = Genre.class,
                        parentColumns = "id_genre",
                        childColumns = "id_genre",
                        onDelete = ForeignKey.CASCADE)
        }
)
public class MovieGenre {
    @PrimaryKey(autoGenerate = true)
    private Integer idMovieGenre;
    @ColumnInfo(name = "id_genre")
    private Integer idGenre;
    @ColumnInfo(name = "id_movie")
    private Integer idMovie;

    public MovieGenre(Integer idGenre, Integer idMovie) {
        this.idGenre = idGenre;
        this.idMovie = idMovie;
    }

    public Integer getIdMovieGenre() {
        return idMovieGenre;
    }

    public void setIdMovieGenre(Integer idMovieGenre) {
        this.idMovieGenre = idMovieGenre;
    }

    public Integer getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(Integer idGenre) {
        this.idGenre = idGenre;
    }

    public Integer getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(Integer idMovie) {
        this.idMovie = idMovie;
    }
}
