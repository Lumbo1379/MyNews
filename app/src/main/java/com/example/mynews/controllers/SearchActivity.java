package com.example.mynews.controllers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mynews.R;
import com.example.mynews.fragments.NewsFragment;
import com.example.mynews.utils.INYTArticles;
import com.example.mynews.utils.NYTCalls;
import com.example.mynews.utils.NYTPageConstants;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity implements NYTCalls.Callbacks {

    private EditText mBeginDate;
    private EditText mEndDate;
    private TextInputLayout mSearchQueryParent;
    private EditText mSearchQueryChild;
    private final Calendar mCalendar = Calendar.getInstance();
    private final SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat mAPIDateFormat = new SimpleDateFormat("yyyyMMdd");
    private HashMap<Integer, String> mCheckboxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mCheckboxes = new HashMap<Integer, String>();

        getViewComponents();

        attachToolbar();
        initialiseDatePickers();
        initialiseSubmitButton();
    }

    private void attachToolbar() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getViewComponents() {
        mBeginDate = findViewById(R.id.activity_search_text_input_begin_date);
        mEndDate = findViewById(R.id.activity_search_text_input_end_date);
        mSearchQueryParent = findViewById(R.id.activity_search_text_input_layout_query);
        mSearchQueryChild = findViewById(R.id.activity_search_edit_text_query);
    }

    private void initialiseDatePickers() {
        DatePickerDialog.OnDateSetListener beginDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText(true);
            }
        };

        mBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SearchActivity.this, beginDate, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText(false);
            }
        };

        mEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SearchActivity.this, endDate, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateDateText(boolean isBegin) {

        if (isBegin) {
            mBeginDate.setText(mDateFormat.format(mCalendar.getTime()));
        } else {
            mEndDate.setText(mDateFormat.format(mCalendar.getTime()));
        }
    }

    private void initialiseSubmitButton() {
        Button button = findViewById(R.id.activity_search_button_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;

                if (mSearchQueryChild.getText().toString().matches("")) {
                    mSearchQueryParent.setError("You must enter a search query!");
                    valid = false;
                }

                if (mCheckboxes.size() == 0) {
                    Toast.makeText(getApplicationContext(),"At least one category must be selected!", Toast.LENGTH_SHORT).show();
                    valid = false;
                }

                if (valid) {
                    NYTCalls.fetchSearched(SearchActivity.this, mSearchQueryChild.getText().toString(), getFilter(), getAPIDate(mBeginDate.getText().toString()), getAPIDate(mEndDate.getText().toString()), NYTPageConstants.API_KEY);
                }
            }
        });
    }

    public void onCheckboxClicked(View view) {
        updateCheckboxes((CheckBox)view);
    }

    private void updateCheckboxes(CheckBox checkBox) {
        int id = checkBox.getId();

        if (mCheckboxes.containsKey(id)) {
            mCheckboxes.remove(id);
        } else {
            mCheckboxes.put(id, checkBox.getText().toString());
        }
    }
    
    private String getFilter() {
        String filter = "news_desk:(";

        for (String value: mCheckboxes.values()) {
            filter += value + " ";
        }

        filter += ")";

        return filter;
    }

    private String getAPIDate(String strDate) {

        Date date = null;

        if (!strDate.isEmpty()) {
            try {
                date = mDateFormat.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (date != null) {
            return mAPIDateFormat.format(date);
        } else {
            return null;
        }

    }

    @Override
    public void onResponse(@Nullable INYTArticles articles) {

        if (articles.getResults().size() > 0) {
            Fragment fragment = new NewsFragment(articles);
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_search_relative_layout, fragment).commit();
        } else {
            Toast.makeText(getApplicationContext(), "Nothing there :(", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure() {

    }
}