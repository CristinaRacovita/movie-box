package com.example.homework.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework.R;
import com.example.homework.adapters.MoviesAdapter;
import com.example.homework.data.MovieAbstract;
import com.example.homework.db.MyDatabase;
import com.example.homework.decorations.MyItemDecoration;
import com.example.homework.listeners.ClickListener;

import java.util.ArrayList;
import java.util.List;

public class ItemListFragment extends Fragment {

    private List<? super MovieAbstract> mMovies;
    private MyDatabase database;
    private boolean movieMode;

    public ItemListFragment() {
    }

    public void setMovieMode(boolean movieMode) {
        this.movieMode = movieMode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View moviesView = inflater.inflate(R.layout.movie_list_layout, container, false);

        database = MyDatabase.getInstance(getActivity().getApplicationContext());

        mMovies = new ArrayList<>();
        if (database != null) {
            if (movieMode) {
                mMovies.addAll(database.getAppDatabase().movieDao().selectFavouriteMovies());
            } else {
                mMovies.addAll(database.getAppDatabase().tvShowDao().selectFavouriteTvShows());
            }
        }

        Toolbar toolbar = moviesView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        RecyclerView mRecyclerView = moviesView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        MoviesAdapter mMoviesAdapter = new MoviesAdapter(getContext(), mMovies, database);
        mRecyclerView.setAdapter(mMoviesAdapter);
        mRecyclerView.setPadding(0, 0, 0, 140);
        mRecyclerView.addItemDecoration(new MyItemDecoration(20));

        ClickListener clickListener = new ClickListener(this, mMoviesAdapter, mMovies);

        mMoviesAdapter.setMovieItemClickListener(clickListener);
        mMoviesAdapter.setLongItemListener(clickListener);

        return moviesView;
    }
}
