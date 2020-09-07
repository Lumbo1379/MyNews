package com.example.mynews.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.mynews.R;

public class ArticleWebActivity extends AppCompatActivity {

    private WebView mBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_web);

        initWebView();

        String url;

        if (savedInstanceState == null) { // Get URL from intent
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                url= null;
            } else {
                url= extras.getString("STRING_URL");
            }
        } else {
            url = (String) savedInstanceState.getSerializable("STRING_URL");
        }

        loadUrl(url);
    }

    private void initWebView() {
        mBrowser = findViewById(R.id.activity_article_web_view);
        WebSettings settings = mBrowser.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
    }

    public void loadUrl(String url) {
        mBrowser.loadUrl(url);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}