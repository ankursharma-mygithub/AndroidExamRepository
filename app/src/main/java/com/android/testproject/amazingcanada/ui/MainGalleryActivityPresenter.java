package com.android.testproject.amazingcanada.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ImageView;

import com.android.testproject.amazingcanada.R;
import com.android.testproject.amazingcanada.common.AppController;
import com.android.testproject.amazingcanada.model.GalleryItem;
import com.android.testproject.amazingcanada.model.GalleryItemsList;

import com.android.testproject.amazingcanada.network.DataFetcherContract;
import com.android.testproject.amazingcanada.network.ImageDownloaderContract;
import com.android.testproject.amazingcanada.network.RetrofitDownloaderService;
import com.android.testproject.amazingcanada.ui.MainGalleryContract.View;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ankursharma on 3/7/18.
 */

/**
 * Presenter for the main activity
 */
public class MainGalleryActivityPresenter implements MainGalleryContract.Presenter {

    private static final String TAG = "MainGalleryPresenter";

    //Main Activity
    private View mGalleryActivity;

    //Model data
    private GalleryItemsList mGalleryItemsList;
    private Context mContext;

    //To manage multiple RxJava subscriptions
    private CompositeSubscription mSubscriptions;

    //To download data using Retrofit and then informing presenter
    private final DataFetcherContract mService;

    //Image downloader
    private ImageDownloaderContract mImageDownloader;

    @Inject
    public MainGalleryActivityPresenter(DataFetcherContract service, View view, ImageDownloaderContract imageDownloader, CompositeSubscription compositeSubscription) {
        mGalleryActivity = view;
        mContext = AppController.getAppController().getApplicationContext();
        mSubscriptions = compositeSubscription;
        mService = service;
        mImageDownloader = imageDownloader;
    }

    /**
     * Gets data from the URL to populate the recyclerview
     */
    @Override
    public void getDataFromURL() {
        if(isNetworkConnected()) {
            mGalleryActivity.showWait();
            Subscription subscription = mService.getItemsList(new RetrofitDownloaderService.GetGalleryItemsListCallback() {
                @Override
                public void onSuccess(GalleryItemsList galleryItemsList) {
                    //If there is an item in the list without any information, don't display.
                    removeNullItemsFromList(galleryItemsList);
                    mGalleryItemsList = galleryItemsList;
                    mGalleryActivity.removeWait();
                    mGalleryActivity.updateTitleBar(galleryItemsList.getTitle());
                    mGalleryActivity.displayListOfItems();
                }

                @Override
                public void onError(String message) {
                    mGalleryActivity.removeWait();
                    mGalleryActivity.showErrorDialog(message);
                }

            });
            mSubscriptions.add(subscription);
        } else {
            mGalleryActivity.removeWait();
            Log.e(TAG, "Internet disconnected");
            mGalleryActivity.showErrorDialog(R.string.internet_not_connected);
        }
    }


    /**
     * Binds item to the individual view holder. Thsi ensures that the list is not
     * maintained at two places.
     * @param holder :: Respective view holder
     * @param position :: position of the view holder
     */
    @Override
    public void onBindItemAtPosition(final MainGalleryContract.RowItemHolder holder, int position) {
        GalleryItem item = mGalleryItemsList.getGalleryItems().get(position);
        holder.updateDescription(item.getDescription());
        holder.updateTitle(item.getTitle());
        String imageUrl = item.getImageUrl();
        final ImageView imageView = holder.getImageView();
        imageView.setVisibility(android.view.View.INVISIBLE);
        if(imageUrl != null && !imageUrl.isEmpty()) {
            mImageDownloader.downloadImage(mContext, imageUrl, holder.getImageView());
        }
    }

    /**
     * Get the number of items in the list.
     * @return
     */
    @Override
    public int getItemsCount() {
        return mGalleryItemsList.getGalleryItems().size();
    }

    @Override
    public void onStop() {
        //In case view is stopped unsubscribe all the observers.
        mSubscriptions.unsubscribe();
    }

    /**
     * Check if the device is connected to internet or not.
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * To remove null items from the list.
     * @param items
     */
    private void removeNullItemsFromList(GalleryItemsList items) {
        //Clear out null values from the list
        for (GalleryItem item : new ArrayList<>(items.getGalleryItems())) {
            if (item.getDescription() == null && item.getImageUrl() == null && item.getTitle() == null) {
                items.getGalleryItems().remove(item);
            }
        }
    }
}
