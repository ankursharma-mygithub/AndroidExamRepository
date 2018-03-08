package com.android.testproject.amazingcanada.di;

import com.android.testproject.amazingcanada.network.DataFetcherContract;
import com.android.testproject.amazingcanada.network.GlideImageDownloader;
import com.android.testproject.amazingcanada.network.ImageDownloaderContract;
import com.android.testproject.amazingcanada.ui.MainGalleryContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ankursharma on 3/8/18.
 */
@Module
public class PresenterModule {
    MainGalleryContract.View mView;
    public PresenterModule(MainGalleryContract.View view) {
        mView = view;
    }

    @Provides
    @Singleton
    public MainGalleryContract.View provideView() {
        return mView;
    }

    @Provides
    @Singleton
    public ImageDownloaderContract provideImageDownloader() {
        return new GlideImageDownloader();
    }

    @Provides
    @Singleton
    public CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }
}
