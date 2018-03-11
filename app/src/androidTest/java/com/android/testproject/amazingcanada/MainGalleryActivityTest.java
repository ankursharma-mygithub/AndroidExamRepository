package com.android.testproject.canadafacts;

/**
 * Created by ankursharma on 3/8/18.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.android.testproject.amazingcanada.R;
import com.android.testproject.amazingcanada.ui.MainGalleryActivity;
import com.android.testproject.amazingcanada.ui.MainGalleryAdapter;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static junit.framework.Assert.assertEquals;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class MainGalleryActivityTest {

    private static final String TAG = "MainGalleryActivityTest";

    private static final long WAIT_TIME = 5000;
    private static final int COUNT_ITEMS = 13;
    @Rule
    public ActivityTestRule<MainGalleryActivity> activityTestRule = new ActivityTestRule<>(MainGalleryActivity.class);

    /**
     * UI test assering screen state when all data is present.
     * Also tests if all the items in the JSON file are listed or not.
     * ASSUMPTION : Device is connected to internet.
     * SUCCESS: When device is connected to internet.
     * FAILS : When device disconnected from internet, and when the items displayed in list is different
     *         different than actual items retrieved from JSON file.
     */
    @Test
    public void checkAllItemsFromJsonLoaded() throws InterruptedException {
        //Put sleep here to wait for recyclerview to initialize.
        try {
            Thread.sleep(WAIT_TIME);
        }catch (InterruptedException e) {
            Log.e(TAG, e.toString());
        }
        if(isNetworkConnected(activityTestRule.getActivity())) {
            //Get recyclerview
            RecyclerView recyclerView = (RecyclerView) activityTestRule.getActivity().findViewById(R.id.recyclerView);
            Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).check(matches(isDisplayed()));
            //Check if all items are loaded or not. Assume number of items in list are 13.
            MainGalleryAdapter adapter = (MainGalleryAdapter)(recyclerView.getAdapter());

            int count = activityTestRule.getActivity().mPresenter.getItemsCount();
            //Ensure that all elements from list are displayed in the recyclerview.
            assertEquals(adapter.getItemCount(), count);
        }
    }

    /**
     * Asserting the screen state in error state(no internet)
     * Assumption : Device is not connected to internet.
     * SUCCESS: When device is disconnected from internet.
     */
    @Test
    public void checkErrorMessageIsDisplayedForNoInternet()  {
        //Put sleep here to wait for recyclerview to initialize.
        try {
            Thread.sleep(WAIT_TIME);
        } catch (InterruptedException e) {
            Log.e(TAG, e.toString());
        }
        //If device is not connected to internet than the applications hould show an error dialog.
        //Here checking if the dialog si displayed with proper message or not.
        String errorMessage = "Please connect to internet to view interested things about Canada.";
        if(!isNetworkConnected(activityTestRule.getActivity())) {
            Espresso.onView(ViewMatchers.withText(errorMessage)).check(matches(isDisplayed()));
        }
    }

    /**
     * Check if the device is connected to internet or not.
     */
    private boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
