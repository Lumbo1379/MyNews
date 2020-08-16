package com.example.mynews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mynews.R;

public class NewsListViewAdapter extends BaseAdapter {

    private String mArticles;
    private LayoutInflater mInflater;

    public NewsListViewAdapter(Context newsFragment, String articles) {

        mArticles = articles;
        mInflater = LayoutInflater.from(newsFragment);
    }

    @Override
    public int getCount() {
        return 3; // TODO: Return number of articles
    }

    @Override
    public Object getItem(int position) {
        return mArticles; // TODO: Return position in articles list
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(R.layout.list_row_article, null);

        TextView headLine = convertView.findViewById(R.id.list_row_article_text_headline);
        headLine.setText(mArticles);

        return convertView;
    }
}
