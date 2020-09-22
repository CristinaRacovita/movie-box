package com.example.homework.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homework.R;
import com.example.homework.adapters.PreviewAdapter;
import com.example.homework.callback.VolleyCallBack;
import com.example.homework.data.Movie;
import com.example.homework.data.MovieAbstract;
import com.example.homework.data.Review;
import com.example.homework.data.TvShow;
import com.example.homework.db.MyDatabase;
import com.example.homework.decorations.MyItemDecoration;
import com.example.homework.listeners.ClickListener;
import com.example.homework.responses.AbstractResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreviewMovieActivity extends AppCompatActivity {

    private List<Review> reviews;
    private MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        database = MyDatabase.getInstance(getApplicationContext());

        Bundle movieBundle = getIntent().getExtras();
        MovieAbstract movie = (MovieAbstract) movieBundle.getSerializable(ClickListener.MOVIE_TAG);
        TextView mTitle = findViewById(R.id.titleText1);
        final LinearLayout mLinearLayout = findViewById(R.id.imageLayout);
        TextView mDate = findViewById(R.id.dateText1);
        TextView mGenre = findViewById(R.id.genreText1);
        TextView mRating = findViewById(R.id.ratingText);
        TextView mOverview = findViewById(R.id.overview);

        String url = "";

        mOverview.setText(movie.getOverview());
        mRating.setText(movie.getVoteAvg() + getString(R.string.ratingstar));

        Picasso.get().load(getString(R.string.urlImages) + movie.getPosterPath()).resize(400, 400).centerCrop().into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mLinearLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        List<Integer> genres = new ArrayList<>();

        if (movie instanceof Movie) {
            url = getString(R.string.searchMovieReviewUrl, ((Movie) movie).getMovieId());
            mTitle.setText(((Movie) movie).getTitle());
            mDate.setText(((Movie) movie).getReleaseDate());
            genres = database.getAppDatabase().movieGenreDao().getGenres(((Movie) movie).getMovieId());
        } else if (movie instanceof TvShow) {
            url = getString(R.string.searchTvReviewUrl, ((TvShow) movie).getTvShowId());
            mTitle.setText(((TvShow) movie).getTitle());
            mDate.setText(((TvShow) movie).getFirstAir());
            genres = database.getAppDatabase().movieGenreDao().getGenres(((TvShow) movie).getTvShowId());
        }

        String genreName = "";

        for (Integer id : genres) {
            genreName += database.getAppDatabase().genreDao().selectGenre(id) + ", ";
        }

        if (genreName.length() > 0) {
            mGenre.setText(genreName.substring(0, genreName.length() - 2));
        }

        getString(new VolleyCallBack() {
            @Override
            public void onSuccessResponse(String data) {
                Gson gson = new Gson();
                Type typeMyType = new TypeToken<AbstractResponse<Review>>() {
                }.getType();
                AbstractResponse<Review> resultGson = gson.fromJson(data, typeMyType);
                reviews = resultGson.getResults();
                RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PreviewMovieActivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                PreviewAdapter mPreviewAdapter = new PreviewAdapter(PreviewMovieActivity.this, reviews);
                mRecyclerView.setAdapter(mPreviewAdapter);
                mRecyclerView.addItemDecoration(new MyItemDecoration(20));
            }
        }, url);

    }

    public void getString(final VolleyCallBack callback, String url) {
        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccessResponse(response);
            }
        }, null);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(strReq);
        queue.start();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}