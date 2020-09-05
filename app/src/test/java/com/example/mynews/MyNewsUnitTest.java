package com.example.mynews;

import com.example.mynews.controllers.SearchActivity;
import com.example.mynews.utils.NYTUtils;

import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class MyNewsUnitTest {

    @Test
    public void validateFilterConcatenation() { // Used to populate news_desk in API calls
        String expectedResult = "news_desk:(Arts Business Politics )";

        HashMap<Integer, String> checkboxes = new HashMap<>();
        checkboxes.put(0, "Arts");
        checkboxes.put(1, "Business");
        checkboxes.put(2, "Politics");

        assertEquals(expectedResult, NYTUtils.getFilter(checkboxes));
    }

    @Test
    public void validateAPIDateParsing() {
        String expectedResult = "20200901";

        assertEquals(expectedResult, NYTUtils.getAPIDate("01/09/2020"));
    }

    @Test
    public void validateUIDateParsing() {
        String expectedResult = "01/09/2020";

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, 9 - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        assertEquals(expectedResult, NYTUtils.getUIDate(calendar.getTime()));
    }
}
