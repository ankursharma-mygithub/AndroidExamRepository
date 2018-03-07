package com.android.testproject.amazingcanada.ui;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.testproject.amazingcanada.R;

public class MainGalleryActivity extends AppCompatActivity implements MainGalleryContract.View{

    private static final String TAG = "MainGalleryActivity";

    //RecyclerView
    private RecyclerView mRecyclerView;

    //Progress bar
    ProgressBar mProgressBar;

    //SwipeRefreshLayout is a part of support library and is a standard way to implement
    //common pull to refresh pattern in Android
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //Presenter class
    private MainGalleryActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gallery);
        //Initialize the views
        initializeViews();

        //Initialize the presenter and get data
        mPresenter = new MainGalleryActivityPresenter(this);
        getAndDisplayListOfItems();
    }

    /**
     * Helper method to initialize the widgets
     */
    private void initializeViews() {
        updateTitleBar("");
        //initialize swipe refresh layout
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Todo:

            }
        });

        //initialize recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainGalleryActivity.this));

        //initialize progressbar
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
    }

    /**
     * Ask presenter to get the data from the specified URL.
     */
    private void getAndDisplayListOfItems() {
        mPresenter.getDataFromURL();
    }


    @Override
    public void updateTitleBar(String titleText) {
        if(titleText != null && !titleText.isEmpty()) {
            getSupportActionBar().setTitle(titleText);
        }
    }

    @Override
    public void displayListOfItems() {
        //Todo:
    }

    @Override
    public void showErrorDialog(int errorId) {
        showErrorDialog(getString(errorId));
    }

    @Override
    public void showErrorDialog(String message) {
        Log.e(TAG, message);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainGalleryActivity.this);
        //To handle use case where user tries to refresh the view and device is not connected.
        builder.setTitle(R.string.error_message)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //For now if any error comes just exit the application.
                        //Todo: This can be later optimized to find different kinds of errors and
                        //prompting the user to take action accordingly
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void showWait() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        mProgressBar.setVisibility(View.GONE);
    }
}
