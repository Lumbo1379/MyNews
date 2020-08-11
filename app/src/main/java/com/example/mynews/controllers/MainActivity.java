package com.example.mynews.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.mynews.R;
import com.example.mynews.adapters.NewsViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 mViewPager;
    private NewsViewPagerAdapter mNewsViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViewComponents();
    }

    private void getViewComponents() {

        mViewPager = findViewById(R.id.activity_main_pager_news);

        mNewsViewPagerAdapter = new NewsViewPagerAdapter();
    }

    private void initialiseViewPager() {

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {

                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                super.onPageScrollStateChanged(state);
            }
        });

        // Orientation set to horizontal by default

        mViewPager.setAdapter(mNewsViewPagerAdapter);
    }
}