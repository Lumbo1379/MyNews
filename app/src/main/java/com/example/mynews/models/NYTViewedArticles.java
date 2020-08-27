package com.example.mynews.models;

import com.example.mynews.utils.INYTArticle;
import com.example.mynews.utils.INYTArticles;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NYTViewedArticles implements INYTArticles {

    @SerializedName("results")
    @Expose
    private List<NYTViewedArticle> results;

    public List<INYTArticle> getResults() {
        return new ArrayList<INYTArticle>(results);
    }

    public void setResults(List<NYTViewedArticle> articles) {
        this.results = articles;
    }
}
