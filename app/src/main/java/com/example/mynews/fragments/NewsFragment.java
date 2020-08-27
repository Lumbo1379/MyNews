package com.example.mynews.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynews.R;
import com.example.mynews.adapters.NewsRecyclerViewAdapter;
import com.example.mynews.models.NYTArticles;
import com.example.mynews.models.NYTViewedArticles;
import com.example.mynews.utils.INYTArticles;
import com.example.mynews.utils.LruImageViewCache;
import com.example.mynews.utils.NYTCalls;
import com.example.mynews.utils.NYTPageConstants;

public class NewsFragment extends Fragment implements NYTCalls.Callbacks {

    private int mSearch;
    private RecyclerView mRecyclerView;

    public NewsFragment(int search) {
        mSearch = search;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mRecyclerView = view.findViewById(R.id.fragment_news_recycler_view_articles);

        getArticles();

        return view;
    }

    private void getArticles() {

        switch (mSearch)
        {
            case NYTPageConstants.TOP_STORIES:
                getTopStories();
                break;
            case NYTPageConstants.MOST_POPULAR:
                getMostPopular();
                break;
            case NYTPageConstants.BUSINESS:
                getBusiness();
                break;
        }
    }

    private void getTopStories() {
        NYTCalls.fetchTopStories(this, "home", NYTPageConstants.API_KEY);
    }

    private void getMostPopular() {
        NYTCalls.fetchMostPopular(this, 1, NYTPageConstants.API_KEY);
    }

    private void getBusiness() {
        NYTCalls.fetchTopStories(this, "business", NYTPageConstants.API_KEY);
    }

    @Override
    public void onResponse(@Nullable INYTArticles articles) {
        updateRecyclerView(articles);
    }

    @Override
    public void onFailure() {

    }

    private void updateRecyclerView(INYTArticles articles) {
        mRecyclerView.setAdapter(new NewsRecyclerViewAdapter(getActivity(), articles));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}