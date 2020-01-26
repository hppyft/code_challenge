package com.arctouch.codechallenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.arctouch.codechallenge.view.HomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<HomeActivity> = ActivityTestRule(HomeActivity::class.java)


    @Test
    fun showList() {
        onView(withId(R.id.no_movies_group)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.home_movies_list)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun openSearchView() {
        onView(withId(R.id.search_icon)).perform(click())
        onView(withId(R.id.search_view)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

}
