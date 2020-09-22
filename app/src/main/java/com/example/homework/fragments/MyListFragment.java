package com.example.homework.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.homework.R;
import com.example.homework.adapters.ViewPagerAdapter;
import com.example.homework.db.MyDatabase;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MyListFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ArrayList<String> tabItemTitles = new ArrayList<>();

    public MyListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myListView = inflater.inflate(R.layout.fragment_my_list, container, false);

        Toolbar toolbar = myListView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.mylist));

        viewPager = myListView.findViewById(R.id.view_pager);
        tabLayout = myListView.findViewById(R.id.tabs);

        tabItemTitles.add(getString(R.string.course1));
        tabItemTitles.add(getString(R.string.tvshows));

        final ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        ItemListFragment movieSFragment = new ItemListFragment();

        movieSFragment.setMovieMode(true);
        ItemListFragment tvShowsFragment2 = new ItemListFragment();
        tvShowsFragment2.setMovieMode(false);

        adapter.addFragment(movieSFragment);
        adapter.addFragment(tvShowsFragment2);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(tabItemTitles.get(position));
                    }
                }).attach();

        return myListView;
    }
}
