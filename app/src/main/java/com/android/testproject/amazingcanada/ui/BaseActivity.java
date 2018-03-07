package com.android.testproject.amazingcanada.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.testproject.amazingcanada.di.DaggerMainActivityComponent;
import com.android.testproject.amazingcanada.di.MainActivityComponent;
import com.android.testproject.amazingcanada.di.RetrofitModule;

/**
 * Created by Ankur Sharma
 */
public class BaseActivity extends AppCompatActivity {
    MainActivityComponent dependency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dependency = DaggerMainActivityComponent.builder().retrofitModule(new RetrofitModule()).build();
    }

    public MainActivityComponent getDeps() {
        return dependency;
    }
}
