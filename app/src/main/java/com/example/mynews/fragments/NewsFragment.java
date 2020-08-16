package com.example.mynews.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mynews.R;
import com.example.mynews.adapters.NewsListViewAdapter;

public class NewsFragment extends Fragment {

    private String mSearch;

    public NewsFragment(String search) {
        mSearch = search;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        String articles = getArticles();
        ListView listView = view.findViewById(R.id.fragment_news_list_view_articles);
        listView.setAdapter(new NewsListViewAdapter(getActivity(), articles));

        return view;
    }

    private String getArticles() {

        switch (mSearch)
        {
            case "0":
                return "TOP STORIES";
            case "1":
                return "MOST POPULAR";
            case "2":
                return "BUSINESS";
            default:
                return "MISSING";
        }
    }
}