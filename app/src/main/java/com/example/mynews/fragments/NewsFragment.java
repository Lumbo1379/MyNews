package com.example.mynews.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mynews.R;
import com.example.mynews.adapters.NewsListViewAdapter;
import com.example.mynews.models.NYTArticle;
import com.example.mynews.models.NYTArticles;
import com.example.mynews.utils.LruImageViewCache;
import com.example.mynews.utils.NYTCalls;
import com.example.mynews.utils.NYTPageConstants;

import java.util.List;

public class NewsFragment extends Fragment implements NYTCalls.Callbacks {

    private int mSearch;
    private ListView mListView;
    private LruImageViewCache mCache;

    public NewsFragment(int search, LruImageViewCache cache) {
        mSearch = search;
        mCache = cache;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mListView = view.findViewById(R.id.fragment_news_list_view_articles);

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

    }

    private void getBusiness() {
        NYTCalls.fetchTopStories(this, "business", NYTPageConstants.API_KEY);
    }

    @Override
    public void onResponse(@Nullable NYTArticles articles) {
        updateListView(articles);
    }

    @Override
    public void onFailure() {

    }

    private void updateListView(NYTArticles articles) {
        mListView.setAdapter(new NewsListViewAdapter(getActivity(), articles, mCache));
    }
}