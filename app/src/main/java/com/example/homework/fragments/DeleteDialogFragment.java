package com.example.homework.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.homework.R;
import com.example.homework.adapters.MoviesAdapter;
import com.example.homework.data.Movie;
import com.example.homework.data.MovieAbstract;
import com.example.homework.data.TvShow;

import java.util.List;

public class DeleteDialogFragment extends DialogFragment {

    private MoviesAdapter mMoviesAdapter;
    private List<? super MovieAbstract> mMovies;
    private int pos;

    public DeleteDialogFragment(MoviesAdapter movieAdapter, List<? super MovieAbstract> movies, int pos) {
        this.mMoviesAdapter = movieAdapter;
        this.mMovies = movies;
        this.pos = pos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View deleteView = inflater.inflate(R.layout.fragment_alert_dialog, container, false);

        Button yesButton = deleteView.findViewById(R.id.btnyes);
        Button cancelButton = deleteView.findViewById(R.id.btncancel);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMovies.get(pos) instanceof Movie) {
                    Toast.makeText(getContext(), ((Movie) mMovies.get(pos)).getTitle() + getString(R.string.delete), Toast.LENGTH_LONG).show();
                } else if (mMovies.get(pos) instanceof TvShow) {
                    Toast.makeText(getContext(), ((TvShow) mMovies.get(pos)).getTitle() + getString(R.string.delete), Toast.LENGTH_LONG).show();
                }
                mMoviesAdapter.removeItem(pos);
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return deleteView;
    }
}
