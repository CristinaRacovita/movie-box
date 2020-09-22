package com.example.homework.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tv_show_genres",
        foreignKeys = {@ForeignKey(entity = TvShow.class,
                parentColumns = "id_tv_show",
                childColumns = "id_tv_show",
                onDelete = ForeignKey.CASCADE),

                @ForeignKey(entity = Genre.class,
                        parentColumns = "id_genre",
                        childColumns = "id_genre",
                        onDelete = ForeignKey.CASCADE)
        }
)
public class TvShowGenre {
    @PrimaryKey
    @ColumnInfo(name = "id_tv_show_genre")
    private Integer idTvShowGenre;
    @ColumnInfo(name = "id_genre")
    private Integer idGenre;
    @ColumnInfo(name = "id_tv_show")
    private Integer idTvShow;

    public TvShowGenre(Integer idGenre, Integer idTvShow) {
        this.idGenre = idGenre;
        this.idTvShow = idTvShow;
    }

    public Integer getIdTvShowGenre() {
        return idTvShowGenre;
    }

    public void setIdTvShowGenre(int idTvShowGenre) {
        this.idTvShowGenre = idTvShowGenre;
    }

    public Integer getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(Integer idGenre) {
        this.idGenre = idGenre;
    }

    public Integer getIdTvShow() {
        return idTvShow;
    }

    public void setIdTvShow(Integer idTvShow) {
        this.idTvShow = idTvShow;
    }
}

