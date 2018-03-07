package com.android.testproject.amazingcanada.network;

import com.android.testproject.amazingcanada.common.Constants;
import com.android.testproject.amazingcanada.model.GalleryItemsList;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by ankursharma on 3/7/18.
 */

/**
 * Used by retrofit library to download JSON file
 */
public interface MyApiEndPoint {
    @GET(Constants.API_END_POINT)
    Observable<GalleryItemsList> getItems();
}
