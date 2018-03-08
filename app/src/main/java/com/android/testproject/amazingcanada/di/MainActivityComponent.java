package com.android.testproject.amazingcanada.di;

import com.android.testproject.amazingcanada.ui.MainGalleryActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ankursharma on 3/7/18.
 */

/**
 * Component part of Dagger2
 */
@Singleton
@Component(modules = {RetrofitModule.class, PresenterModule.class})
public interface MainActivityComponent {
    void inject(MainGalleryActivity mainGalleryActivity);
}
