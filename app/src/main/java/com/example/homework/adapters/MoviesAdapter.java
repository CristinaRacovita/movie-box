package com.example.homework.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework.R;
import com.example.homework.data.Movie;
import com.example.homework.data.MovieAbstract;
import com.example.homework.data.MovieGenre;
import com.example.homework.data.TvShow;
import com.example.homework.data.TvShowGenre;
import com.example.homework.db.MyDatabase;
import com.example.homework.fragments.AddManualFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<? super MovieAbstract> mMovies;
    private OnMyItemClickListener mMovieListener;
    private OnMyItemClickListener mLongItemListener;
    private MyDatabase myDatabase;
    private Context context;
    private final Integer MAX_NO_OF_MOVIES = 20;

    public MoviesAdapter(Context context, List<? super MovieAbstract> movies, MyDatabase myDatabase) {
        this.mMovies = movies;
        this.myDatabase = myDatabase;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        MovieAbstract movie = (MovieAbstract) mMovies.get(position);
        holder.bind(movie, holder, position);
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        }
        if (mMovies.size() > 20) {
            return MAX_NO_OF_MOVIES;
        } else {
            return mMovies.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mGenre;
        private TextView mReleaseDate;
        private ToggleButton mFavourite;
        private ImageView mView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.movieImage);
            mTitle = itemView.findViewById(R.id.title_id);
            mGenre = itemView.findViewById(R.id.genre_id);
            mReleaseDate = itemView.findViewById(R.id.date_id);
            mFavourite = itemView.findViewById(R.id.favourite_id);
        }

        private void bind(final MovieAbstract movie, final ViewHolder holder, final int position) {

            mFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    movie.setFavourite(isChecked);
                    if (movie instanceof Movie) {
                        myDatabase.getAppDatabase().movieDao().updateMovie((Movie) movie);
                    } else if (movie instanceof TvShow) {
                        myDatabase.getAppDatabase().tvShowDao().updateTvShow((TvShow) movie);
                    }
                }
            });

            final String drawablePath = movie.getPosterPath();
            if (drawablePath != null && !drawablePath.equals(AddManualFragment.DEFAULT_PATH)) {
                Picasso.get().load(context.getString(R.string.urlImages) + drawablePath).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        mView.setImageDrawable(new BitmapDrawable(context.getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }

            if (mFavourite != null) {
                if (movie.isFavourite() != null) {
                    mFavourite.setChecked(movie.isFavourite());
                } else {
                    mFavourite.setChecked(false);
                }
            }

            List<Integer> genres = new ArrayList<>();

            if (movie instanceof Movie) {
                mTitle.setText(((Movie) movie).getTitle());
                mReleaseDate.setText(((Movie) movie).getReleaseDate());
                genres = myDatabase.getAppDatabase().movieGenreDao().getGenres(((Movie) movie).getMovieId());
            } else if (movie instanceof TvShow) {
                mTitle.setText(((TvShow) movie).getTitle());
                mReleaseDate.setText(((TvShow) movie).getFirstAir());
                genres = myDatabase.getAppDatabase().movieGenreDao().getGenres(((TvShow) movie).getTvShowId());
            }

            String genreName = "";

            for (Integer id : genres) {
                genreName += myDatabase.getAppDatabase().genreDao().selectGenre(id) + ", ";
            }

            if (genreName.length() > 0) {
                mGenre.setText(genreName.substring(0, genreName.length() - 2));
            }

            if (mMovieListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (myDatabase != null) {
                            mMovieListener.onMovieClickListener(position);
                        }
                    }
                });
            }

            if (mLongItemListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mLongItemListener.onLongClickListener(position);
                        return true;
                    }
                });
            }

        }
    }

    public interface OnMyItemClickListener {
        void onLongClickListener(int pos);

        void onMovieClickListener(int pos);
    }

    public void setMovieItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        this.mMovieListener = onMyItemClickListener;
    }

    public void setLongItemListener(OnMyItemClickListener onMyItemClickListener) {
        this.mLongItemListener = onMyItemClickListener;
    }

    public void removeItem(int pos) {
        if (mMovies.get(pos) instanceof Movie) {
            myDatabase.getAppDatabase().movieDao().deleteMovie((Movie) mMovies.get(pos));
        } else if (mMovies.get(pos) instanceof TvShow) {
            myDatabase.getAppDatabase().tvShowDao().deleteTvShow((TvShow) mMovies.get(pos));
        }
        mMovies.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, getItemCount());
    }

    public void refresh(List<? super MovieAbstract> movies) {
        myDatabase.getAppDatabase().movieDao().deleteAll();
        myDatabase.getAppDatabase().tvShowDao().deleteAll();
        this.mMovies = movies;
        List<MovieGenre> movieGenres = new ArrayList<>();
        List<TvShowGenre> tvShowGenres = new ArrayList<>();
        for (Object movieAbstract : mMovies) {
            if (movieAbstract instanceof Movie) {
                myDatabase.getAppDatabase().movieDao().insertMovie((Movie) movieAbstract);
                if (((Movie) movieAbstract).getGenreIds() != null) {
                    for (Integer id : ((Movie) movieAbstract).getGenreIds()) {
                        MovieGenre movieGenre = new MovieGenre(id, ((Movie) movieAbstract).getMovieId());
                        movieGenres.add(movieGenre);
                    }
                }
            } else if (movieAbstract instanceof TvShow) {
                myDatabase.getAppDatabase().tvShowDao().insertTvShow((TvShow) movieAbstract);
                if (((TvShow) movieAbstract).getGenreIds() != null) {
                    for (Integer id : ((TvShow) movieAbstract).getGenreIds()) {
                        TvShowGenre tvShowGenre = new TvShowGenre(id, ((TvShow) movieAbstract).getTvShowId());
                        tvShowGenres.add(tvShowGenre);
                    }
                }
            }
        }
        myDatabase.getAppDatabase().movieGenreDao().insertAll(movieGenres);
        myDatabase.getAppDatabase().tvShowGenreDao().insertAll(tvShowGenres);
        notifyDataSetChanged();
    }

}
