package com.example.homework.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.homework.R;
import com.example.homework.activities.MainActivity;
import com.example.homework.activities.SingInActivity;
import com.example.homework.services.ForegroundService;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.homework.activities.SingInActivity.REMEMBER_TAG;
import static com.example.homework.activities.SingInActivity.USERNAME_TAG;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View profileView = inflater.inflate(R.layout.profile_layout, container, false);

        Toolbar toolbar = profileView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        String username_value = getActivity().getIntent().getStringExtra(USERNAME_TAG);
        if (username_value == null) {
            username_value = getString(R.string.guest);
        }

        final TextView name = profileView.findViewById(R.id.name_text);
        final TextView email = profileView.findViewById(R.id.email_text);
        String email_Set;

        if (username_value.equals(getString(R.string.guest))) {
            email_Set = getString(R.string.emaildontexist);
        } else {
            email_Set = username_value + getString(R.string.gmail);
        }

        name.setText(username_value);
        email.setText(email_Set);

        final CollapsingToolbarLayout collapsingToolbarLayout = profileView.findViewById(R.id.collapse_toolbar);
        AppBarLayout appBarLayout = profileView.findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.profileText));
                    isShow = true;
                    ((AppCompatActivity) getActivity()).getSupportActionBar().show();

                } else if (isShow) {

                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                    ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                }
            }
        });

        FloatingActionButton logout = profileView.findViewById(R.id.logout_fab);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mSharedPreferences = getActivity().getApplicationContext().getSharedPreferences(MainActivity.PREFERENCES_TAG, Context.MODE_PRIVATE);
                SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                mEditor.putBoolean(REMEMBER_TAG, false);
                mEditor.apply();
                Intent serviceIntent = new Intent(getContext(), ForegroundService.class);
                getActivity().stopService(serviceIntent);
                Intent signInIntent = new Intent(getContext(), SingInActivity.class);
                startActivity(signInIntent);
            }
        });

        return profileView;
    }
}