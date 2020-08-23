package com.example.mynews.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynews.R;
import com.example.mynews.models.NYTArticle;
import com.example.mynews.models.NYTArticles;
import com.example.mynews.utils.DownloadImage;
import com.example.mynews.utils.LruImageViewCache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    private NYTArticles mArticles;
    private LruImageViewCache mCache;

    public NewsRecyclerViewAdapter(Context newsFragment, NYTArticles articles, LruImageViewCache cache) {
        mArticles = articles;
        mCache = cache;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_row_article, parent, false);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.updateWithArticle(mArticles.getResults().get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount()  {
        return mArticles.getResults().size();
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

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void updateWithArticle(NYTArticle article) {
            TextView headLine = mView.findViewById(R.id.list_row_article_text_headline);
            headLine.setText(article.getTitle());

            TextView date = mView.findViewById(R.id.list_row_article_text_date);
            date.setText(getShortDate(article.getPublishedDate()));

            ImageView snapshot = mView.findViewById(R.id.list_row_layout_image_snapshot);

            String url = article.getMultimedia().get(0).getUrl();
            final Bitmap bitmap = mCache.getBitmapFromMemCache(url);

            if (bitmap != null) {
                snapshot.setImageBitmap(bitmap);
            } else {
                new DownloadImage(snapshot, mCache).execute(url);
            }
        }
    }
}
