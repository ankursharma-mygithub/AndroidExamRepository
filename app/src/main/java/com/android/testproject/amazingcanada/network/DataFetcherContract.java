package com.android.testproject.amazingcanada.network;

import com.android.testproject.amazingcanada.model.GalleryItemsList;

import rx.Subscription;

/**
 * Created by ankursharma on 3/8/18.
 */

public interface DataFetcherContract {
    Subscription getItemsList(final GetGalleryItemsListCallback callback);
    interface GetGalleryItemsListCallback{
        void onSuccess(GalleryItemsList galleryItemsList);
        void onError(String message);
    }
}
