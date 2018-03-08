package com.android.testproject.amazingcanada.common;

import android.app.Application;

//import com.android.testproject.amazingcanada.di.DaggerMainActivityComponent;
import com.android.testproject.amazingcanada.di.MainActivityComponent;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ankursharma on 3/7/18.
 */

/**
 * The extended application class
 */
public class AppController extends Application {
    private static AppController sAppController;
    private MainActivityComponent dependency;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppController = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public static AppController getAppController() {
        return sAppController;
    }

    public MainActivityComponent getDeps() {
        return dependency;
    }
}
