package com.project.demorecord;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void EmptyNameAgeTest() {
        onView(withId(R.id.editTExtName)).check(matches(withText("")));
        onView(withId(R.id.editTextAge)).check(matches(withText("")));
        onView(withId(R.id.buttonAdded)).perform(click());
        onView(withText("Please Enter user info")).check(matches(isDisplayed()));
    }

    @Test
    public void EmptyNameTest() {
        onView(withId(R.id.editTExtName)).check(matches(withText("")));
        onView(withId(R.id.editTextAge)).perform(replaceText("20"));
        onView(withId(R.id.buttonAdded)).perform(click());
        onView(withText("Please Enter user info")).check(matches(isDisplayed()));
    }

    @Test
    public void EmptyListTest() {
        onView(withId(R.id.buttonGotoList)).perform(click());
        onView(withText("Not Found")).check(matches(isDisplayed()));
    }

    @Test
    public void EmptyAgeTest() {
        onView(withId(R.id.editTextAge)).check(matches(withText("")));
        onView(withId(R.id.editTExtName)).perform(replaceText("YING"));
        onView(withId(R.id.buttonAdded)).perform(click());
        onView(withText("Please Enter user info")).check(matches(isDisplayed()));
    }
}
