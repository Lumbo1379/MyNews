package com.example.mynews.models;

import com.example.mynews.utils.INYTArticle;
import com.example.mynews.utils.INYTArticles;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NYTSearchedArticles implements INYTArticles {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("response")
    @Expose
    private NYTResponse response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public NYTResponse getResponse() {
        return response;
    }

    public void setResponse(NYTResponse response) {
        this.response = response;
    }

    @Override
    public List<INYTArticle> getResults() {
        return new ArrayList<INYTArticle>(response.getDocs());
    }
}
