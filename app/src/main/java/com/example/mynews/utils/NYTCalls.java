package com.example.mynews.utils;

import android.content.Context;

import androidx.annotation.Nullable;

import com.example.mynews.models.NYTArticles;
import com.example.mynews.models.NYTSearchedArticle;
import com.example.mynews.models.NYTSearchedArticles;
import com.example.mynews.models.NYTViewedArticle;
import com.example.mynews.models.NYTViewedArticles;

import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NYTCalls {

    public interface Callbacks {

        void onResponse(@Nullable INYTArticles articles);
        void onFailure();
    }

    private static Retrofit mRetrofit;

    public static void fetchTopStories(Callbacks callbacks, String section, String apiKey) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks); // WeakReference in order for Garbage Collector to delete as needed and avoid memory leaks

        INewYorkTimesService nytService = mRetrofit.create(INewYorkTimesService.class);

        Call<NYTArticles> call = nytService.getTopStories(section, apiKey);
        call.enqueue(new Callback<NYTArticles>() {

            @Override
            public void onResponse(Call<NYTArticles> call, Response<NYTArticles> response) {

                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<NYTArticles> call, Throwable t) {

                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }

    public static void fetchMostPopular(Callbacks callbacks, int period, String apiKey) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks); // WeakReference in order for Garbage Collector to delete as needed and avoid memory leaks

        INewYorkTimesService nytService = mRetrofit.create(INewYorkTimesService.class);

        Call<NYTViewedArticles> call = nytService.getMostPopular(period, apiKey);
        call.enqueue(new Callback<NYTViewedArticles>() {

            @Override
            public void onResponse(Call<NYTViewedArticles> call, Response<NYTViewedArticles> response) {

                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<NYTViewedArticles> call, Throwable t) {

                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }

    public static void fetchSearched(Callbacks callbacks, String query, String filter, String beginDate, String endDate, String apiKey) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks); // WeakReference in order for Garbage Collector to delete as needed and avoid memory leaks

        INewYorkTimesService nytService = mRetrofit.create(INewYorkTimesService.class);

        Call<NYTSearchedArticles> call = nytService.getSearched(query, filter, beginDate, endDate, apiKey);
        call.enqueue(new Callback<NYTSearchedArticles>() {

            @Override
            public void onResponse(Call<NYTSearchedArticles> call, Response<NYTSearchedArticles> response) {

                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<NYTSearchedArticles> call, Throwable t) {

                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }

    public static void init(Context context) {
        long cacheSize = 5 * 1024 * 1024;
        Cache cache = new Cache(context.getCacheDir(), cacheSize);

        OkHttpClient client = new OkHttpClient() // Create our own OkHttpClient to cache articles headlines for offline use
                .newBuilder()
                .cache(cache)
                .addInterceptor(chain -> {
                    Request request = chain.request();

                    if (NetworkConnectivity.isNetworkConnected(context))
                    {
                        request.newBuilder()
                                .addHeader("Cache-Control", "public, max-age=" + 5)
                                .build();
                    } else {
                        request.newBuilder()
                                .addHeader("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                                .build();
                    }

                    return chain.proceed(request);
                }).build();

        mRetrofit = new Retrofit.Builder() // Init retrofit with our OkHttpClient
                .baseUrl("https://api.nytimes.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
