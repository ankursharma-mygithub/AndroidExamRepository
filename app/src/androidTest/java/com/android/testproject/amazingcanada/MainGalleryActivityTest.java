package com.android.testproject.amazingcanada;

/**
 * Created by ankursharma on 3/7/18.
 */

import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;


import com.android.testproject.amazingcanada.ui.MainGalleryActivity;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class MainGalleryActivityTest {
    @Rule
    public ActivityTestRule<MainGalleryActivity> activityRule = new ActivityTestRule<>(MainGalleryActivity.class);

    @Before
    public void resetTimeout() {
        IdlingPolicies.setMasterPolicyTimeout(DateUtils.SECOND_IN_MILLIS * 3, TimeUnit.SECONDS);
        IdlingPolicies.setIdlingResourceTimeout(DateUtils.SECOND_IN_MILLIS * 3, TimeUnit.SECONDS);
    }

    //BELOW ARE THE TWO INSTRUMENTED TESTS AND ONE OF THIS WILL FAIL IF THE INPUT IS NOT CORRECT.
    //Scroll to a particular position in recylerview
    @Test
    public void test1ScrollToPosition() {
        //Assume waiting time as 3 seconds - meanwhile the UI sets up.
        final long waitingTime = DateUtils.SECOND_IN_MILLIS * 3;

        // Now we wait
        IdlingResource idlingResource = new ElapsedIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResource);

        onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToPosition(8));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Clean up
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    //Test click on any of the item of recyclerview
    @Test
    public void test2ClickOnListItem() {
        //Assume waiting time as 1 seconds - meanwhile the UI sets up.
        final long waitingTime = DateUtils.SECOND_IN_MILLIS * 1;
        // Now we wait
        IdlingResource idlingResource = new ElapsedIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResource);

        onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2,
                        click()));

        // Clean up
       IdlingRegistry.getInstance().unregister(idlingResource);
    }

}
