package com.example.mynews.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mynews.R;
import com.example.mynews.models.NYTArticle;
import com.example.mynews.models.NYTArticles;
import com.example.mynews.utils.DownloadImage;
import com.example.mynews.utils.LruImageViewCache;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class NewsListViewAdapter extends BaseAdapter {

    private NYTArticles mArticles;
    private LayoutInflater mInflater;
    private LruImageViewCache mCache;

    public NewsListViewAdapter(Context newsFragment, NYTArticles articles, LruImageViewCache cache) {

        mArticles = articles;
        mInflater = LayoutInflater.from(newsFragment);
        mCache = cache;
    }

    @Override
    public int getCount() {
        return mArticles.getResults().size();
    }

    @Override
    public Object getItem(int position) { return mArticles.getResults().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(R.layout.list_row_article, null);

        TextView headLine = convertView.findViewById(R.id.list_row_article_text_headline);
        headLine.setText(mArticles.getResults().get(position).getTitle());

        TextView date = convertView.findViewById(R.id.list_row_article_text_date);
        date.setText(getShortDate(mArticles.getResults().get(position).getPublishedDate()));

        ImageView snapshot = convertView.findViewById(R.id.list_row_layout_image_snapshot);
        String url = mArticles.getResults().get(position).getMultimedia().get(0).getUrl();
        final Bitmap bitmap = mCache.getBitmapFromMemCache(url);

        if (bitmap != null) {
            snapshot.setImageBitmap(bitmap);
        } else {
            new DownloadImage(snapshot, mCache).execute(url);
        }

        return convertView;
    }

    private String getShortDate(String date) {
        Date dateTime = null;

        try {
            dateTime = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new SimpleDateFormat("yyyy-MM-dd").format(dateTime);
    }
}
