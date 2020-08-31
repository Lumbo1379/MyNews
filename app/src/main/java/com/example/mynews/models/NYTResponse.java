package com.example.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NYTResponse {
    @SerializedName("docs")
    @Expose
    private List<NYTSearchedArticle> docs = null;
    @SerializedName("meta")
    @Expose
    private NYTMeta meta;

    public List<NYTSearchedArticle> getDocs() {
        return docs;
    }

    public void setDocs(List<NYTSearchedArticle> docs) {
        this.docs = docs;
    }

    public NYTMeta getMeta() {
        return meta;
    }

    public void setMeta(NYTMeta meta) {
        this.meta = meta;
    }

}
