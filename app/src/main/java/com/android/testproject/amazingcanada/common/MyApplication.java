package com.android.testproject.amazingcanada.common;

import android.app.Application;

/**
 * Created by ankursharma on 3/7/18.
 */

public class MyApplication extends Application {
    private static MyApplication sMyApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sMyApp = this;
    }

    public static MyApplication getMyApp() {
        return sMyApp;
    }
}
