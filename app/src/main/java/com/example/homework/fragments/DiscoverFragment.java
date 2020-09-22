package com.example.homework.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homework.R;
import com.example.homework.adapters.MoviesAdapter;
import com.example.homework.callback.VolleyCallBack;
import com.example.homework.data.Movie;
import com.example.homework.data.MovieAbstract;
import com.example.homework.data.TvShow;
import com.example.homework.db.MyDatabase;
import com.example.homework.decorations.MyItemDecoration;
import com.example.homework.listeners.ClickListener;
import com.example.homework.responses.AbstractResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {

    private MoviesAdapter mMoviesAdapter;
    private List<? super MovieAbstract> mMovies;
    private MyDatabase database;
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;

    public enum Mode {
        Both("Both"),
        Movie("Movie"),
        TvShow("Tv Show");

        private String displayName;

        Mode(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }

    }

    public DiscoverFragment() {
        mMovies = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View discoverView = inflater.inflate(R.layout.movie_list_layout, container, false);

        database = MyDatabase.getInstance(getActivity().getApplicationContext());

        setHasOptionsMenu(true);
        toolbar = discoverView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.discover);

        getString(new VolleyCallBack() {
            @Override
            public void onSuccessResponse(String data) {
                Gson gson = new Gson();
                Type typeMyType = new TypeToken<AbstractResponse<Movie>>() {
                }.getType();
                AbstractResponse<Movie> resultGson = gson.fromJson(data, typeMyType);
                mMovies.addAll(resultGson.getResults());

                mRecyclerView = discoverView.findViewById(R.id.recycler_view);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mMoviesAdapter = new MoviesAdapter(getContext(), mMovies, database);
                mRecyclerView.setAdapter(mMoviesAdapter);
                mRecyclerView.setPadding(0, 150, 0, 140);
                mRecyclerView.addItemDecoration(new MyItemDecoration(20));

                mMoviesAdapter.refresh(mMovies);

                ClickListener clickListener = new ClickListener(DiscoverFragment.this, mMoviesAdapter, mMovies);

                mMoviesAdapter.setMovieItemClickListener(clickListener);
                mMoviesAdapter.setLongItemListener(clickListener);
            }
        }, getString(R.string.searchMovieUrl, "A"));

        return discoverView;
    }

    public void getString(final VolleyCallBack callback, String url) {
        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccessResponse(response);
            }
        }, null);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(strReq);
        queue.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.menu_layout, menu);
        final MenuItem searchItem = menu.findItem(R.id.search_bar);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(700);
        searchView.setQueryHint(getString(R.string.searhhint));

        final Spinner[] spinnerMode = new Spinner[1];
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerMode[0] = new Spinner(getActivity());

                spinnerMode[0].setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Mode.values()));
                spinnerMode[0].setGravity(Gravity.END);
                searchView.setGravity(Gravity.START);

                toolbar.setTitle("");
                toolbar.addView(spinnerMode[0]);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                toolbar.removeView(spinnerMode[0]);
                toolbar.setTitle(R.string.discover);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                spinnerMode[0].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    List<? super MovieAbstract> results;

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        results = new ArrayList<>();
                        switch (position) {
                            case 0:
                                getString(new VolleyCallBack() {
                                    @Override
                                    public void onSuccessResponse(String data) {
                                        Gson gson = new Gson();
                                        Type typeMyType1 = new TypeToken<AbstractResponse<Movie>>() {
                                        }.getType();
                                        Type typeMyType2 = new TypeToken<AbstractResponse<TvShow>>() {
                                        }.getType();

                                        List<Movie> movies = new ArrayList<>();
                                        List<TvShow> tvShows = new ArrayList<>();

                                        AbstractResponse<Movie> moviesGson = gson.fromJson(data, typeMyType1);
                                        AbstractResponse<TvShow> tvshowGson = gson.fromJson(data, typeMyType2);

                                        for (Movie movie : moviesGson.getResults()) {
                                            if (movie.getTitle() != null) {
                                                movies.add(movie);
                                            }
                                        }
                                        for (TvShow tvShow : tvshowGson.getResults()) {
                                            if (tvShow.getTitle() != null) {
                                                tvShows.add(tvShow);
                                            }
                                        }
                                        results.addAll(movies);
                                        results.addAll(tvShows);

                                        mMoviesAdapter.refresh(results);
                                    }
                                }, getString(R.string.searchMultiUrl, query));
                                break;
                            case 1:
                                getString(new VolleyCallBack() {
                                    @Override
                                    public void onSuccessResponse(String data) {
                                        Gson gson = new Gson();
                                        Type typeMyType = new TypeToken<AbstractResponse<Movie>>() {
                                        }.getType();
                                        AbstractResponse<Movie> resultGson = gson.fromJson(data, typeMyType);
                                        results.addAll(resultGson.getResults());
                                        mMoviesAdapter.refresh(results);
                                    }
                                }, getString(R.string.searchMovieUrl, query));
                                break;
                            case 2:
                                getString(new VolleyCallBack() {
                                    @Override
                                    public void onSuccessResponse(String data) {
                                        Gson gson = new Gson();
                                        Type typeMyType = new TypeToken<AbstractResponse<TvShow>>() {
                                        }.getType();
                                        AbstractResponse<TvShow> resultGson = gson.fromJson(data, typeMyType);
                                        results.addAll(resultGson.getResults());
                                        mMoviesAdapter.refresh(results);
                                    }
                                }, getString(R.string.searchTvShowUrl, query));
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                return false;
            }
        });

        MenuItem addItem = menu.findItem(R.id.add);
        addItem.setVisible(false);
        MenuItem refreshItem = menu.findItem(R.id.refresh);
        refreshItem.setVisible(false);
    }

}