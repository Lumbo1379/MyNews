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
import com.example.mynews.utils.INYTArticle;
import com.example.mynews.utils.INYTArticles;
import com.example.mynews.utils.LruImageViewCache;
import com.example.mynews.utils.NYTCalls;
import com.example.mynews.utils.NYTPageConstants;

public class NewsFragment extends Fragment implements NYTCalls.Callbacks {

    private int mSearch;
    private RecyclerView mRecyclerView;
    private INYTArticles mSearchedArticles;

    public NewsFragment(int search) {
        mSearch = search;
    }

    public NewsFragment(INYTArticles articles) { // If loading from searched article section
        mSearch = -1;
        mSearchedArticles = articles;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mRecyclerView = view.findViewById(R.id.fragment_news_recycler_view_articles);

        getArticles();

        return view;
    }

    private void getArticles() { // Get articles based on viewpager position or if came from search activity

        switch (mSearch)
        {
            case NYTPageConstants.SEARCHED:
                updateRecyclerView(mSearchedArticles);
                break;
            case NYTPageConstants.TOP_STORIES:
                getTopStories();
                break;
            case NYTPageConstants.MOST_POPULAR:
                getMostPopular();
                break;
            case NYTPageConstants.BUSINESS:
                getBusiness();
                break;
            case NYTPageConstants.ARTS:
                getArts();
                break;
            case NYTPageConstants.ENTREPRENEURSHIP:
                getEntrepreneurship();
                break;
            case NYTPageConstants.POLITICS:
                getPolitics();
                break;
            case NYTPageConstants.SPORTS:
                getSports();
                break;
            case NYTPageConstants.TRAVEL:
                getTravel();
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

    private void getArts() {
        NYTCalls.fetchTopStories(this, "arts", NYTPageConstants.API_KEY);
    }

    private void getEntrepreneurship() {
        NYTCalls.fetchSearched(this, "entrepreneurship", null, null, null, NYTPageConstants.API_KEY); // Entrepreneur is not under top stories, but under topics
    }

    private void getPolitics() {
        NYTCalls.fetchTopStories(this, "politics", NYTPageConstants.API_KEY);
    }

    private void getSports() {
        NYTCalls.fetchTopStories(this, "sports", NYTPageConstants.API_KEY);
    }

    private void getTravel() {
        NYTCalls.fetchTopStories(this, "travel", NYTPageConstants.API_KEY);
    }

    @Override
    public void onResponse(@Nullable INYTArticles articles) {
        updateRecyclerView(articles);
    }

    @Override
    public void onFailure() {

    }

    private void updateRecyclerView(INYTArticles articles) { // Update list of articles based on returned articles
        mRecyclerView.setAdapter(new NewsRecyclerViewAdapter(getActivity(), articles));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}