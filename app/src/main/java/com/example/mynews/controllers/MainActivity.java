package com.example.mynews.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mynews.R;
import com.example.mynews.adapters.NewsViewPagerAdapter;
import com.example.mynews.fragments.NewsFragment;
import com.example.mynews.utils.LruImageViewCache;
import com.example.mynews.utils.NYTCalls;
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
        attachToolbar();
        initialiseViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_activity_main_more:
                return true;
            case R.id.menu_activity_main_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getViewComponents() {

        mViewPager = findViewById(R.id.activity_main_pager_news);

        NYTCalls.init(this);

        mNewsViewPagerAdapter = new NewsViewPagerAdapter(this);
    }

    private void initialiseViewPager() {

        // Orientation set to horizontal by default

        mViewPager.setAdapter(mNewsViewPagerAdapter);

        attachTabLayout();
    }

    private void attachToolbar() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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