package com.example.mynews.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class NYTUtils {

    private static final SimpleDateFormat mAPIDateFormat = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static String getFilter(HashMap<Integer, String> checkboxes) { // Converting checkboxes into appropriate string
        String filter = "news_desk:(";

        for (String value: checkboxes.values()) {
            filter += value + " ";
        }

        filter += ")";

        return filter;
    }

    public static String getAPIDate(String strDate) { // Converting date into appropriate API one
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

    public static String getUIDate(Date date) { // Converting date from API call into a more suitable one for display
        return mDateFormat.format(date);
    }
}
