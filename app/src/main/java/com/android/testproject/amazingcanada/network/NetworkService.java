package com.android.testproject.amazingcanada.network;

import com.android.testproject.amazingcanada.model.GalleryItemsList;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ankursharma on 3/7/18.
 */

/**
 * Uses RxJava and Retrofit library to dfownload and parse JSON file.
 */
public class NetworkService {
    private final MyApiEndPoint mApiEndPoint;

    public NetworkService(MyApiEndPoint apiEndPoint) {
        mApiEndPoint = apiEndPoint;
    }

    public Subscription getItemsList(final GetGalleryItemsListCallback callback) {

        return mApiEndPoint.getItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends GalleryItemsList>>() {
                    @Override
                    public Observable<? extends GalleryItemsList> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<GalleryItemsList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());

                    }

                    @Override
                    public void onNext(GalleryItemsList cityListResponse) {
                        callback.onSuccess(cityListResponse);

                    }
                });
    }

    //Callback Interface to be implemented by the presenter
    public interface GetGalleryItemsListCallback{
        void onSuccess(GalleryItemsList galleryItemsList);
        void onError(String message);
    }
}
