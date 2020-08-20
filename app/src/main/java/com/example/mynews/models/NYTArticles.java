package com.example.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NYTArticles {

    @SerializedName("results")
    @Expose
    private List<NYTArticle> results;

    public List<NYTArticle> getResults() {
        return results;
    }

    public void setResults(List<NYTArticle> articles) {
        this.results = articles;
    }
}
