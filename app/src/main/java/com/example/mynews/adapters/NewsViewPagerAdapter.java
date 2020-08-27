package com.example.mynews.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mynews.fragments.NewsFragment;
import com.example.mynews.utils.LruImageViewCache;


public class NewsViewPagerAdapter extends FragmentStateAdapter {

    public NewsViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new NewsFragment(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
