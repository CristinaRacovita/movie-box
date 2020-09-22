package com.example.homework.listeners;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.homework.R;
import com.example.homework.activities.PreviewMovieActivity;
import com.example.homework.adapters.MoviesAdapter;
import com.example.homework.data.Movie;
import com.example.homework.data.MovieAbstract;
import com.example.homework.data.TvShow;
import com.example.homework.fragments.DeleteDialogFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class ClickListener implements MoviesAdapter.OnMyItemClickListener {
    public enum Option {
        DELETE("Delete this movie"),
        CALENDAR("Add as event");

        private String displayName;

        Option(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }

    }

    private MoviesAdapter mMoviesAdapter;
    private List<? super MovieAbstract> mMovies;
    private Fragment fragment;
    private String TAG = "DELETE_ITEM";
    public static final String MOVIE_TAG = "movie";
    public static final String DEFAULT_HOUR = " 12:00";

    public ClickListener(Fragment fragment, MoviesAdapter mMoviesAdapter, List<? super MovieAbstract> movies) {
        this.fragment = fragment;
        this.mMovies = movies;
        this.mMoviesAdapter = mMoviesAdapter;
    }

    @Override
    public void onLongClickListener(final int pos) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(fragment.getActivity());
        alertBuilder.setTitle(fragment.getActivity().getString(R.string.pickoption));
        final Option[] values = Option.values();
        CharSequence[] options = new CharSequence[values.length];
        for (int i = 0; i < values.length; i++) {
            options[i] = values[i].toString();
        }
        alertBuilder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        FragmentManager fragmentManager = fragment.getActivity().getSupportFragmentManager();
                        DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment(mMoviesAdapter, mMovies, pos);
                        deleteDialogFragment.show(fragmentManager, TAG);
                        break;
                    case 1:
                        MovieAbstract movie = (MovieAbstract) mMovies.get(pos);
                        try {
                            pushEventToCalender(movie);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(fragment.getContext(), fragment.getActivity().getString(R.string.event), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        alertBuilder.show();
    }

    @Override
    public void onMovieClickListener(int pos) {
        MovieAbstract movie = (MovieAbstract) mMovies.get(pos);
        Bundle movieBundle = new Bundle();
        movieBundle.putSerializable(MOVIE_TAG, movie);
        Intent intent = new Intent(fragment.getContext(), PreviewMovieActivity.class);
        intent.putExtras(movieBundle);
        fragment.startActivity(intent);
    }

    public void pushEventToCalender(MovieAbstract movieAbstract) throws ParseException {

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.ENGLISH);
        Date date = null;
        if (movieAbstract instanceof Movie) {
            date = format.parse(((Movie) movieAbstract).getReleaseDate() + DEFAULT_HOUR);
        } else if (movieAbstract instanceof TvShow) {
            date = format.parse(((TvShow) movieAbstract).getFirstAir() + DEFAULT_HOUR);
        }

        checkPermission(42, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR);
        checkPermission(20, Manifest.permission.WRITE_CALENDAR, Manifest.permission.WRITE_CALENDAR);

        ContentResolver contentResolver = fragment.getActivity().getContentResolver();
        ContentValues contentValues = new ContentValues();

        if (movieAbstract instanceof Movie) {
            contentValues.put(CalendarContract.Events.TITLE, ((Movie) movieAbstract).getTitle());
        } else if (movieAbstract instanceof TvShow) {
            contentValues.put(CalendarContract.Events.TITLE, ((TvShow) movieAbstract).getTitle());
        }
        contentValues.put(CalendarContract.Events.DESCRIPTION, movieAbstract.getOverview());
        contentValues.put(CalendarContract.Events.DTSTART, date.getTime());
        contentValues.put(CalendarContract.Events.DTEND, date.getTime() + 2 * 60 * 60 * 1000);
        contentValues.put(CalendarContract.Events.CALENDAR_ID, 1);
        contentValues.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());

        Uri calendarUri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, contentValues);
        calendarUri.buildUpon();

    }

    private void checkPermission(int callbackId, String... permissionsId) {
        boolean permissions = true;
        for (String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(fragment.getContext(), p) == PERMISSION_GRANTED;
        }

        if (!permissions)
            ActivityCompat.requestPermissions(fragment.getActivity(), permissionsId, callbackId);
    }
}
