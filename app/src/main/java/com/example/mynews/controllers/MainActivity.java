package com.example.mynews.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.mynews.R;
import com.example.mynews.adapters.NewsViewPagerAdapter;
import com.example.mynews.fragments.NewsFragment;
import com.example.mynews.utils.LruImageViewCache;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 mViewPager;
    private NewsViewPagerAdapter mNewsViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViewComponents();
        initialiseViewPager();
    }

    private void getViewComponents() {

        mViewPager = findViewById(R.id.activity_main_pager_news);

        LruImageViewCache cache = new LruImageViewCache();

        mNewsViewPagerAdapter = new NewsViewPagerAdapter(this, cache);
    }

    private void initialiseViewPager() {

        // Orientation set to horizontal by default

        mViewPager.setAdapter(mNewsViewPagerAdapter);

        attachTabLayout();
    }

    private void attachTabLayout() {
        TabLayout tabLayout = findViewById(R.id.activity_main_tab_layout);

        new TabLayoutMediator(tabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: {
                        tab.setText("TOP STORIES");
                        break;
                    }
                    case 1: {
                        tab.setText("MOST POPULAR");
                        break;
                    }
                    case 2: {
                        tab.setText("BUSINESS");
                        break;
                    }
                }
            }
        }).attach();
    }
}