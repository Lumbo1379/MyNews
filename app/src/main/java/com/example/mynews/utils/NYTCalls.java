package com.example.mynews.utils;

import androidx.annotation.Nullable;

import com.example.mynews.models.NYTArticles;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NYTCalls {

    public interface Callbacks {

        void onResponse(@Nullable NYTArticles articles);
        void onFailure();
    }

    public static void fetchTopStories(Callbacks callbacks, String section, String apiKey) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks); // WeakReference in order for Garbage Collector to delete as needed and avoid memory leaks

        INewYorkTimesService nytService = INewYorkTimesService.retrofit.create(INewYorkTimesService.class);

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
}
