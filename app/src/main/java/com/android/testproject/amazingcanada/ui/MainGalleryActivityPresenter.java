package com.android.testproject.amazingcanada.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.testproject.amazingcanada.R;
import com.android.testproject.amazingcanada.common.MyApplication;
import com.android.testproject.amazingcanada.model.GalleryItem;
import com.android.testproject.amazingcanada.model.GalleryItemsList;

import com.android.testproject.amazingcanada.network.AppPicassoImageDownloader;
import com.android.testproject.amazingcanada.network.ImageDownloader;
import com.android.testproject.amazingcanada.network.NetworkService;
import com.android.testproject.amazingcanada.ui.MainGalleryContract.View;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ankursharma on 3/7/18.
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
    private final NetworkService mService;

    //Image downloader
    private ImageDownloader mImageDownloader;

    public MainGalleryActivityPresenter(NetworkService service, View view) {
        mGalleryActivity = view;
        mContext = MyApplication.getMyApp().getApplicationContext();
        mSubscriptions = new CompositeSubscription();
        mService = service;
        mImageDownloader = new AppPicassoImageDownloader();
    }

    /**
     * Gets data from the URL to populate the recyclerview
     */
    @Override
    public void getDataFromURL() {
        if(isNetworkConnected()) {
            mGalleryActivity.showWait();
            Subscription subscription = mService.getItemsList(new NetworkService.GetGalleryItemsListCallback() {
                @Override
                public void onSuccess(GalleryItemsList galleryItemsList) {
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
            Log.e(TAG, "Internet disconnecte");
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
    public void onBindItemAtPosition(MainGalleryContract.RowItemHolder holder, int position) {
        GalleryItem item = mGalleryItemsList.getGalleryItems().get(position);
        holder.updateDescription(item.getDescription());
        holder.updateTitle(item.getTitle());
        holder.getImageView().setVisibility(android.view.View.INVISIBLE);
        String imageUrl = item.getImageUrl();
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

    /**
     * Check if the device is connected to internet or not.
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
