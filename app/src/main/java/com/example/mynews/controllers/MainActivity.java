package com.example.mynews.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;
import androidx.appcompat.widget.Toolbar;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager2 mViewPager;
    private NewsViewPagerAdapter mNewsViewPagerAdapter;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViewComponents();
        attachToolbar();
        attachNavigationView();
        attachDrawerLayout();
        initialiseViewPager();
        createNotificationChannel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.menu_activity_main_more:
                return true;
            case R.id.menu_activity_main_search:
                intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_activity_main_notifications:
                intent = new Intent(MainActivity.this, NotificationsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.activity_main_drawer_top_stories:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.activity_main_drawer_most_popular:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.activity_main_drawer_business:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.activity_main_drawer_arts:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.activity_main_drawer_entrepreneurship:
                mViewPager.setCurrentItem(4);
                break;
            case R.id.activity_main_drawer_politics:
                mViewPager.setCurrentItem(5);
                break;
            case R.id.activity_main_drawer_sports:
                mViewPager.setCurrentItem(6);
                break;
            case R.id.activity_main_drawer_travel:
                mViewPager.setCurrentItem(7);
                break;
            default:
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return false; // Leave unchecked
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
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void attachDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void attachNavigationView() {
        mNavigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
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
                    case 3: {
                        tab.setText("ARTS");
                        break;
                    }
                    case 4: {
                        tab.setText("ENTREPRENEURSHIP");
                        break;
                    }
                    case 5: {
                        tab.setText("POLITICS");
                        break;
                    }
                    case 6: {
                        tab.setText("SPORTS");
                        break;
                    }
                    case 7: {
                        tab.setText("TRAVEL");
                        break;
                    }
                }
            }
        }).attach();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "News Article Channel";
            String description = "Notification for new articles from selected categories";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NotificationsActivity.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}