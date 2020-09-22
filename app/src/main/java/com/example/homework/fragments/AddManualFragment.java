package com.example.homework.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.homework.R;
import com.example.homework.data.Movie;
import com.example.homework.data.MovieGenre;
import com.example.homework.data.TvShow;
import com.example.homework.db.MyDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddManualFragment extends Fragment implements DateFragment.OnProcessData {

    public enum GenreEnum {
        Adventure,
        Action,
        Animation,
        Romance,
        Horror,
        Drama,
        Comedy,
        Biography,
        Fantasy,
        SF
    }

    private Spinner spinnerGenre;
    private Spinner spinnerType;
    private View mView;
    public static final String DEFAULT_PATH = "";
    public final String EMPTY_STRING = "";
    private MyDatabase myDatabase;

    public enum MovieType {
        Movie,
        TvShow
    }

    public AddManualFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.add_movie_layout, container, false);

        myDatabase = MyDatabase.getInstance(getActivity().getApplicationContext());

        Toolbar toolbar = mView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        spinnerGenre = mView.findViewById(R.id.spinner);
        spinnerGenre.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, GenreEnum.values()));

        spinnerType = mView.findViewById(R.id.moviespinner);
        spinnerType.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, MovieType.values()));

        addMode();

        FloatingActionButton addButton = mView.findViewById(R.id.add_fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView releaseDateView = mView.findViewById(R.id.dateText);
                CheckBox isFavouriteView = mView.findViewById(R.id.favourite_checkbox);
                EditText titleEditText = mView.findViewById(R.id.titleEditText);
                EditText ratingEditText = mView.findViewById(R.id.ratingEditText);
                EditText directorEditText = mView.findViewById(R.id.directorEditText);

                if (!titleEditText.getText().toString().equals(EMPTY_STRING) &&
                        !ratingEditText.getText().toString().equals(EMPTY_STRING) &&
                        !directorEditText.getText().toString().equals(EMPTY_STRING) &&
                        !releaseDateView.getText().toString().equals(getString(R.string.date))) {

                    String type = spinnerType.getSelectedItem().toString();
                    if (type.equals(MovieType.Movie.toString())) {
                        Movie newMovie = new Movie(DEFAULT_PATH,
                                null,
                                null,
                                null,
                                Double.parseDouble(ratingEditText.getText().toString()),
                                null,
                                null,
                                isFavouriteView.isChecked(),
                                directorEditText.getText().toString(),
                                null,
                                null,
                                releaseDateView.getText().toString(),
                                titleEditText.getText().toString(),
                                titleEditText.getText().toString()
                        );

                        long movieId = myDatabase.getAppDatabase().movieDao().insertMovie(newMovie);
                        Integer genreId = myDatabase.getAppDatabase().genreDao().selectGenre(spinnerGenre.getSelectedItem().toString());
                        myDatabase.getAppDatabase().movieGenreDao().insert(new MovieGenre(genreId, (int) movieId));
                        Toast.makeText(getContext(), getString(R.string.moviesaved), Toast.LENGTH_LONG).show();


                    } else if (type.equals(MovieType.TvShow.toString())) {
                        TvShow newTvShow = new TvShow(DEFAULT_PATH,
                                null,
                                null,
                                null,
                                null,
                                null, null, isFavouriteView.isChecked(),
                                directorEditText.getText().toString(),
                                null, releaseDateView.getText().toString(),
                                titleEditText.getText().toString(),
                                titleEditText.getText().toString(), Double.parseDouble(ratingEditText.getText().toString()));

                        myDatabase.getAppDatabase().tvShowDao().insertTvShow(newTvShow);
                        Toast.makeText(getContext(), getString(R.string.tvshowsaved), Toast.LENGTH_LONG).show();

                    }
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.my_current_fragment, new DiscoverFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(getContext(), getString(R.string.incomplete), Toast.LENGTH_LONG).show();
                }

            }
        });

        return mView;
    }

    public void addMode() {
        LinearLayout dateLayout = mView.findViewById(R.id.release_date);
        final DateFragment dateFragment = new DateFragment(this);
        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFragment.show(getActivity().getSupportFragmentManager(), getString(R.string.datepicker));
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.addmovie));
    }

    @Override
    public void processDatePickerResult(int year, int month, int day) {
        TextView releaseDate = mView.findViewById(R.id.dateText);

        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (day_string + "/" + month_string + "/" + year_string);

        releaseDate.setTextColor(getActivity().getColor(R.color.Black));
        releaseDate.setText(dateMessage);
        Toast.makeText(getActivity(), getString(R.string.date) + dateMessage, Toast.LENGTH_SHORT).show();

    }
}
