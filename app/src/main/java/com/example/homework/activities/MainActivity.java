package com.example.homework.activities;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homework.R;
import com.example.homework.callback.VolleyCallBack;
import com.example.homework.data.User;
import com.example.homework.db.MyDatabase;
import com.example.homework.fragments.AddManualFragment;
import com.example.homework.fragments.DiscoverFragment;
import com.example.homework.fragments.MyListFragment;
import com.example.homework.fragments.ProfileFragment;
import com.example.homework.responses.GenreResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentTransaction transaction;
    private MyDatabase database;
    public static final String PREFERENCES_TAG = "MyPreferences";
    private final String INSERT_KEY = "INSERT_KEY";
    private SharedPreferences mSharedPreferences;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = MyDatabase.getInstance(getApplicationContext());
        makeTransaction(new DiscoverFragment());

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_profile:
                        makeTransaction(new ProfileFragment());
                        break;
                    case R.id.action_discover:
                        makeTransaction(new DiscoverFragment());
                        break;
                    case R.id.action_my_list:
                        makeTransaction(new MyListFragment());
                        break;
                    case R.id.action_manual_add:
                        makeTransaction(new AddManualFragment());
                        break;
                }
                return true;
            }
        });


    }

    public void makeTransaction(Fragment fragment) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.my_current_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
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

    @Override
    protected void onResume() {
        super.onResume();
        mSharedPreferences = getApplicationContext().getSharedPreferences(PREFERENCES_TAG, MODE_PRIVATE);

        Boolean alreadyInsert = mSharedPreferences.getBoolean(INSERT_KEY, false);
        if (!alreadyInsert) {
            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
            mEditor.putBoolean(INSERT_KEY, true);
            mEditor.apply();
            getString(new VolleyCallBack() {
                @Override
                public void onSuccessResponse(String data) {
                    addGenres(data);
                }
            }, getString(R.string.genreMovieUrl));
            getString(new VolleyCallBack() {
                @Override
                public void onSuccessResponse(String data) {
                    addGenres(data);
                }
            }, getString(R.string.genreTvUrl));
            database.getAppDatabase().userDao().insertUser(new User(null, "cristina", RegisterActivity.md5("admin"), null, null, null));
        }
    }

    public void addGenres(String data) {
        Gson gson = new Gson();
        GenreResponse resultGson = gson.fromJson(data, GenreResponse.class);
        database.getAppDatabase().genreDao().insertAll(resultGson.getGenres());
    }
}
