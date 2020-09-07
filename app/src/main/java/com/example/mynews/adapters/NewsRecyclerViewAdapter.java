package com.example.mynews.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynews.R;
import com.example.mynews.controllers.ArticleWebActivity;
import com.example.mynews.controllers.MainActivity;
import com.example.mynews.models.NYTArticle;
import com.example.mynews.models.NYTArticles;
import com.example.mynews.models.NYTViewedArticles;
import com.example.mynews.utils.DownloadImage;
import com.example.mynews.utils.INYTArticle;
import com.example.mynews.utils.INYTArticles;
import com.example.mynews.utils.LruImageViewCache;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    private INYTArticles mArticles; // INYTArticle implements commonly used methods required to display all article types
    private Context mContext;

    public NewsRecyclerViewAdapter(Context newsFragment, INYTArticles articles) { // Get context and articles from fragment
        mArticles = articles;
        mContext = newsFragment;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_row_article, parent, false); // Inflate single list item article view into recycler view

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.updateWithArticle(mArticles.getResults().get(position), position); // Set up list item article with appropriate values
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount()  {
        return mArticles.getResults().size();
    }

    private String getShortDate(String date) { // Truncate date
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

        public void updateWithArticle(INYTArticle article, int position) { // Set values for article list row item
            TextView headLine = mView.findViewById(R.id.list_row_article_text_headline);
            headLine.setText(article.getTitle());

            TextView date = mView.findViewById(R.id.list_row_article_text_date);
            date.setText(getShortDate(article.getPublishedDate()));

            TextView category = mView.findViewById(R.id.list_row_article_text_category);
            category.setText(article.getFullSection());

            ImageView snapshot = mView.findViewById(R.id.list_row_layout_image_snapshot);
            String url = article.getSnapshotUrl();

            if (url != "") { // If there is media to display
                Picasso.get().load(url).into(snapshot); // Download, cache and display
            } else {
                snapshot.setImageBitmap(null); // Otherwise don't display an image, and remove one if one was loaded
            }

            mView.setTag(position); // Set tag, used by OnClickListener

            mView.setOnClickListener(new View.OnClickListener() { // When clicking and article load the source in a WebView
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ArticleWebActivity.class);
                    intent.putExtra("STRING_URL",  mArticles.getResults().get((int)v.getTag()).getUrl()); // Pass the URL to be loaded by the WebView
                    mContext.startActivity(intent); // Load into ArticleWebActivity
                }
            });
        }
    }
}
