package com.example.mynews.controllers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import com.example.mynews.R;
import com.example.mynews.receivers.NotificationReceiver;

import java.util.Calendar;
import java.util.HashMap;

public class NotificationsActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "NEW_ARTICLES_CHANNEL";
    public static final String PREF_KEY_NOTIFICATIONS_ENABLED = "PREF_KEY_NOTIFICATIONS_ENABLED";
    public static final String PREF_KEY_ARTS_ENABLED = "PREF_KEY_ARTS_ENABLED";
    public static final String PREF_KEY_BUSINESS_ENABLED = "PREF_KEY_BUSINESS_ENABLED";
    public static final String PREF_KEY_ENTREPRENEURSHIP_ENABLED = "PREF_KEY_ENTREPRENEURS_ENABLED";
    public static final String PREF_KEY_POLITICS_ENABLED = "PREF_KEY_POLITICS_ENABLED";
    public static final String PREF_KEY_SPORTS_ENABLED = "PREF_KEY_SPORTS_ENABLED";
    public static final String PREF_KEY_TRAVEL_ENABLED = "PREF_KEY_TRAVEL_ENABLED";
    public static final String PREF_KEY_SEARCH_QUERY_TERM = "PREF_KEY_SEARCH_QUERY_TERM";

    private Switch mSwitch;
    private EditText mEditText;
    private HashMap<Integer, String> mCheckboxes;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        mCheckboxes = new HashMap<Integer, String>(); // For storing selected checkboxes locally
        mPreferences = getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE); // Give name to preferences for use by receiver

        getViewComponents();

        attachToolbar();
        setViewOnPreferences(); // Init view with preferences applied
        initialiseSwitch();
        initialiseEditText();
    }

    private void getViewComponents() {
        mSwitch = findViewById(R.id.activity_notifications_switch_notifications);
        mEditText = findViewById(R.id.activity_notifications_edit_text_query);
    }

    private void attachToolbar() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initialiseSwitch() { // If notifications are toggled or not
        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckboxes.size() == 0) { // If no required topic checkboxes are checked
                    setNotificationSwitchUnchecked();
                    cancelAlarm();
                    return;
                }

                if (mSwitch.isChecked()) {
                    setAlarm(mPreferences, getApplicationContext());
                } else { // Don't manually set switch as unchecked as was unchecked by user
                    cancelAlarm();
                }
            }
        });
    }

    public void onCheckboxClicked(View view) {
        updateCheckboxes((CheckBox)view);
    }

    private void updateCheckboxes(CheckBox checkBox) {
        int id = checkBox.getId();

        if (mCheckboxes.containsKey(id)) { // Remove checkbox from local dictionary
            mCheckboxes.remove(id);
        } else {
            mCheckboxes.put(id, checkBox.getText().toString()); // Add checkbox to local dictionary
        }

        if (mCheckboxes.size() == 0) { // If all notifications unchecked uncheck notifications and cancel alarm
            setNotificationSwitchUnchecked();
            cancelAlarm();
        }

        switch (id) // Update checkbox preferences
        {
            case R.id.activity_notifications_checkbox_arts:
                mPreferences.edit().putBoolean(PREF_KEY_ARTS_ENABLED, checkBox.isChecked()).apply();
                break;
            case R.id.activity_notifications_checkbox_business:
                mPreferences.edit().putBoolean(PREF_KEY_BUSINESS_ENABLED, checkBox.isChecked()).apply();
                break;
            case R.id.activity_notifications_checkbox_entrepreneurship:
                mPreferences.edit().putBoolean(PREF_KEY_ENTREPRENEURSHIP_ENABLED, checkBox.isChecked()).apply();
                break;
            case R.id.activity_notifications_checkbox_politics:
                mPreferences.edit().putBoolean(PREF_KEY_POLITICS_ENABLED, checkBox.isChecked()).apply();
                break;
            case R.id.activity_notifications_checkbox_sports:
                mPreferences.edit().putBoolean(PREF_KEY_SPORTS_ENABLED, checkBox.isChecked()).apply();
                break;
            case R.id.activity_notifications_checkbox_travel:
                mPreferences.edit().putBoolean(PREF_KEY_TRAVEL_ENABLED, checkBox.isChecked()).apply();
                break;
        }
    }

    private void setNotificationSwitchUnchecked() {
        mSwitch.setChecked(false);
    }

    public static void setAlarm(SharedPreferences preferences, Context context) {
        preferences.edit().putBoolean(PREF_KEY_NOTIFICATIONS_ENABLED, true).apply(); // Set notification pref to enabled

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setAction(CHANNEL_ID);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); // Update alarm if one already exists

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8); // Set time to trigger (8am)
        calendar.add(Calendar.DATE, 1); // Add a day to not immediately send notification if after 8am when set

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, alarmIntent); // Set repeat frequency (every 24 hrs)
    }

    private void cancelAlarm() {
        mPreferences.edit().putBoolean(PREF_KEY_NOTIFICATIONS_ENABLED, false).apply();

        Intent intent  = new Intent(this, NotificationReceiver.class);
        intent.setAction(CHANNEL_ID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_NO_CREATE);
        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        if(pendingIntent != null) { // If there is an alarm to cancel
            alarmManager.cancel(pendingIntent);
        }
    }

    private void initialiseEditText() { // Update query pref
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPreferences.edit().putString(PREF_KEY_SEARCH_QUERY_TERM, s.toString()).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setViewOnPreferences() { // Update view with preferences
        mEditText.setText(mPreferences.getString(PREF_KEY_SEARCH_QUERY_TERM, ""));

        mSwitch.setChecked(mPreferences.getBoolean(PREF_KEY_NOTIFICATIONS_ENABLED, false));

        setCheckboxFromPreferences(findViewById(R.id.activity_notifications_checkbox_arts), mPreferences.getBoolean(PREF_KEY_ARTS_ENABLED, false));
        setCheckboxFromPreferences(findViewById(R.id.activity_notifications_checkbox_business), mPreferences.getBoolean(PREF_KEY_BUSINESS_ENABLED, false));
        setCheckboxFromPreferences(findViewById(R.id.activity_notifications_checkbox_entrepreneurship), mPreferences.getBoolean(PREF_KEY_ENTREPRENEURSHIP_ENABLED, false));
        setCheckboxFromPreferences(findViewById(R.id.activity_notifications_checkbox_politics), mPreferences.getBoolean(PREF_KEY_POLITICS_ENABLED, false));
        setCheckboxFromPreferences(findViewById(R.id.activity_notifications_checkbox_sports), mPreferences.getBoolean(PREF_KEY_SPORTS_ENABLED, false));
        setCheckboxFromPreferences(findViewById(R.id.activity_notifications_checkbox_travel), mPreferences.getBoolean(PREF_KEY_TRAVEL_ENABLED, false));
    }

    private void setCheckboxFromPreferences(CheckBox checkBox, boolean checked) { // Update local checkbox store
        if (checked) {
            checkBox.setChecked(true);
            mCheckboxes.put(checkBox.getId(), checkBox.getText().toString());
        }
    }
}