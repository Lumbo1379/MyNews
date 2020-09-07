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
import com.example.mynews.utils.NYTUtils;
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

    private void initialiseDatePickers() { // Set date text based on selection
        DatePickerDialog.OnDateSetListener beginDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText(true);
            }
        };

        mBeginDate.setOnClickListener(new View.OnClickListener() { // Open date picker
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

    private void updateDateText(boolean isBegin) { // Apply date text to correct UI

        if (isBegin) {
            mBeginDate.setText(NYTUtils.getUIDate(mCalendar.getTime()));
        } else {
            mEndDate.setText(NYTUtils.getUIDate(mCalendar.getTime()));
        }
    }

    private void initialiseSubmitButton() {
        Button button = findViewById(R.id.activity_search_button_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;

                if (mSearchQueryChild.getText().toString().matches("")) { // If the query is left empty
                    mSearchQueryParent.setError("You must enter a search query!");
                    valid = false;
                }

                if (mCheckboxes.size() == 0) { // If not one checkbox has been selected
                    Toast.makeText(getApplicationContext(),"At least one category must be selected!", Toast.LENGTH_SHORT).show();
                    valid = false;
                }

                if (valid) { // If both checks have passed
                    NYTCalls.fetchSearched(SearchActivity.this, mSearchQueryChild.getText().toString(), NYTUtils.getFilter(mCheckboxes), NYTUtils.getAPIDate(mBeginDate.getText().toString()), NYTUtils.getAPIDate(mEndDate.getText().toString()), NYTPageConstants.API_KEY);
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

    @Override
    public void onResponse(@Nullable INYTArticles articles) {

        if (articles.getResults().size() > 0) { // If returned with results based on selected options
            Fragment fragment = new NewsFragment(articles);
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_search_relative_layout, fragment).commit();
        } else { // If no articles were found matching criteria
            Toast.makeText(getApplicationContext(), "Nothing there :(", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure() {

    }
}