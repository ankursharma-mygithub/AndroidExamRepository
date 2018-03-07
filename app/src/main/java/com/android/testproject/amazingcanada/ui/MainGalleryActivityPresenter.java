package com.android.testproject.amazingcanada.ui;

import android.content.Context;

import com.android.testproject.amazingcanada.common.MyApplication;
import com.android.testproject.amazingcanada.model.GalleryItemsList;

import com.android.testproject.amazingcanada.ui.MainGalleryContract.View;

/**
 * Created by ankursharma on 3/7/18.
 */

public class MainGalleryActivityPresenter implements MainGalleryContract.Presenter {

    private static final String TAG = "MainGalleryActivityPresenter";

    //Main Activity
    private View mGalleryActivity;

    //Model data
    private GalleryItemsList mGalleryItemsList;
    private Context mContext;

    public MainGalleryActivityPresenter(View view) {
        mGalleryActivity = view;
        mContext = MyApplication.getMyApp().getApplicationContext();
    }

    /**
     * Gets data from the URL to populate the recyclerview
     */
    @Override
    public void getDataFromURL() {
        //Todo:
    }


    /**
     * Binds item to the individual view holder. Thsi ensures that the list is not
     * maintained at two places.
     * @param holder :: Respective view holder
     * @param position :: position of the view holder
     */
    @Override
    public void onBindItemAtPosition(MainGalleryContract.RowItemHolder holder, int position) {
        //Todo:
    }

    /**
     * Get the number of items in the list.
     * @return
     */
    @Override
    public int getItemsCount() {
        return mGalleryItemsList.getGalleryItems().size();
    }
}
