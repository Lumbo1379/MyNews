package com.example.mynews.receivers;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mynews.R;
import com.example.mynews.controllers.MainActivity;
import com.example.mynews.controllers.NotificationsActivity;
import com.example.mynews.utils.INYTArticles;
import com.example.mynews.utils.NYTCalls;
import com.example.mynews.utils.NYTPageConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotificationReceiver extends BroadcastReceiver implements NYTCalls.Callbacks {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            SharedPreferences preferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

            if (preferences.getBoolean(NotificationsActivity.PREF_KEY_NOTIFICATIONS_ENABLED, false)) {
                NotificationsActivity.setAlarm(preferences, context);
            }
        } else {
            checkForArticles();
        }
    }

    private void sendNewArticlesNotification() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, NotificationsActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_article_black_18dp)
                .setContentTitle("New Articles")
                .setContentText("New important articles to read!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); // Remove notification when tapped

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);

        notificationManager.notify(1, builder.build());
    }

    private void checkForArticles() {
        SharedPreferences preferences = mContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        String query = preferences.getString(NotificationsActivity.PREF_KEY_SEARCH_QUERY_TERM, null);

        String filter = "news_desk:(";
        filter += getFilter(preferences.getBoolean(NotificationsActivity.PREF_KEY_ARTS_ENABLED, false), "Arts");
        filter += getFilter(preferences.getBoolean(NotificationsActivity.PREF_KEY_BUSINESS_ENABLED, false), "Business");
        filter += getFilter(preferences.getBoolean(NotificationsActivity.PREF_KEY_ENTREPRENEURSHIP_ENABLED, false), "Entrepreneurs");
        filter += getFilter(preferences.getBoolean(NotificationsActivity.PREF_KEY_POLITICS_ENABLED, false), "Politics");
        filter += getFilter(preferences.getBoolean(NotificationsActivity.PREF_KEY_SPORTS_ENABLED, false), "Sports");
        filter += getFilter(preferences.getBoolean(NotificationsActivity.PREF_KEY_TRAVEL_ENABLED, false), "Travel");
        filter += ")";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        String beginDate = dateFormat.format(calendar.getTime());

        NYTCalls.fetchSearched(NotificationReceiver.this, query, filter, beginDate, null, NYTPageConstants.API_KEY);
    }

    private String getFilter(Boolean isChecked, String tag) {
        if (isChecked)
            return tag + " ";

        return "";
    }

    @Override
    public void onResponse(@Nullable INYTArticles articles) {
        if (articles.getResults().size() > 0)
            sendNewArticlesNotification();
    }

    @Override
    public void onFailure() {
        int foo = 1;
    }
}
