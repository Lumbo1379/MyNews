package com.example.mynews.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mynews.fragments.NewsFragment;
import com.example.mynews.utils.LruImageViewCache;


public class NewsViewPagerAdapter extends FragmentStateAdapter {

    private LruImageViewCache mCache;

    public NewsViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, LruImageViewCache cache) {
        super(fragmentActivity);

        mCache = cache;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new NewsFragment(position, mCache);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
