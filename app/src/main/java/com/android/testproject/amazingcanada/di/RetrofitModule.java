package com.android.testproject.amazingcanada.di;

import com.android.testproject.amazingcanada.common.Constants;
import com.android.testproject.amazingcanada.network.DataFetcherContract;
import com.android.testproject.amazingcanada.network.MyApiEndPoint;
import com.android.testproject.amazingcanada.network.RetrofitDownloaderService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ankursharma on 3/7/18.
 */

/**
 * Module part of Dagger2 dependency injection framework
 */
@Module
public class RetrofitModule {

    @Provides
    @Singleton
    Retrofit provideCall() {


        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public MyApiEndPoint providesNetworkService(
            Retrofit retrofit) {
        return retrofit.create(MyApiEndPoint.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public DataFetcherContract providesService(
            MyApiEndPoint networkService) {
        return new RetrofitDownloaderService(networkService);
    }

//    @Provides
//    @Singleton
//    public MainGalleryContract.View provideView() {
//        return mView;
//    }
}
