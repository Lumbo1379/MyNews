package com.example.mynews.utils;

import com.example.mynews.models.NYTArticle;
import com.example.mynews.models.NYTArticles;
import com.example.mynews.models.NYTViewedArticles;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface INewYorkTimesService {

    @GET("svc/topstories/v2/{section}.json")
    Call<NYTArticles> getTopStories( @Path("section") String section, @Query("api-key") String apiKey);

    @GET("svc/mostpopular/v2/viewed/{period}.json")
    Call<NYTViewedArticles> getMostPopular(@Path("period") int period, @Query("api-key") String apiKey);
}
