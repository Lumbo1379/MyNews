package com.example.mynews.utils;

import com.example.mynews.models.NYTArticle;
import com.example.mynews.models.NYTArticles;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface INewYorkTimesService {

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("svc/topstories/v2/{section}.json")
    Call<NYTArticles> getTopStories( @Path("section") String section, @Query("api-key") String apiKey);
}
