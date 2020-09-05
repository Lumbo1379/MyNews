package com.example.mynews;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.mynews.controllers.ArticleWebActivity;
import com.example.mynews.controllers.MainActivity;
import com.example.mynews.controllers.NotificationsActivity;
import com.example.mynews.controllers.SearchActivity;
import com.google.android.material.tabs.TabLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void testSearchButton() {
        onView(withId(R.id.menu_activity_main_search)).perform(click());

        intended(hasComponent(SearchActivity.class.getName()));
    }

    @Test
    public void testNotificationsButton() {
        onView(withId(R.id.menu_activity_main_more)).perform(click());
        onView(withText("Notifications")).perform(click());

        intended(hasComponent(NotificationsActivity.class.getName()));
    }

    @Test
    public void testArticleOpenedToWebView() {
        onView(withId(R.id.fragment_news_recycler_view_articles)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent(ArticleWebActivity.class.getName()));
    }

    @Test
    public void testViewPagerSwipeTopStoriesToMostPopular() {
        onView(withId(R.id.activity_main_pager_news)).perform(swipeLeft());

        TabLayout tabLayout = mActivityTestRule.getActivity().findViewById(R.id.activity_main_tab_layout);
        assertEquals(tabLayout.getSelectedTabPosition(), 1);
    }
}
