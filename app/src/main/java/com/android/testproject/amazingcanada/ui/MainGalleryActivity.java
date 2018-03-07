package com.android.testproject.amazingcanada.ui;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.testproject.amazingcanada.R;
import com.android.testproject.amazingcanada.network.NetworkService;

import javax.inject.Inject;

/**
 * Main and only activity of the application.
 */
public class MainGalleryActivity extends BaseActivity implements MainGalleryContract.View{

    private static final String TAG = "MainGalleryActivity";

    //RecyclerView
    private RecyclerView mRecyclerView;

    //Progress bar
    private ProgressBar mProgressBar;

    //SwipeRefreshLayout is a part of support library and is a standard way to implement
    //common pull to refresh pattern in Android
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //Presenter class
    private MainGalleryContract.Presenter mPresenter;

    //Inject using Dagger2
    @Inject
    public NetworkService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);
        setContentView(R.layout.activity_main_gallery);
        //Initialize the views
        initializeViews();
        //Initialize the presenter and get data
        mPresenter = new MainGalleryActivityPresenter(mService, this);
        getAndDisplayListOfItems();
    }

    /**
     * Helper method to initialize the widgets
     */
    private void initializeViews() {
        //reset title bar text to empty text.
        //This will be updated from the JSON data.
        updateTitleBar("");
        //initialize swipe refresh layout
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                getAndDisplayListOfItems();
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
        if(mPresenter != null) {
            mPresenter.getDataFromURL();
        }
    }


    /**
     * Update title bar of the main view.
     * @param titleText : title to be updated
     */
    @Override
    public void updateTitleBar(String titleText) {
        if(titleText != null && !titleText.isEmpty()) {
            getSupportActionBar().setTitle(titleText);
        }
    }

    /**
     * Called by presenter once the data is ready.
     */
    @Override
    public void displayListOfItems() {
        if(mRecyclerView != null) {
            mRecyclerView.setAdapter(new MainGalleryAdapter(mPresenter));
        }
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

    /**
     * Display progress bar
     */
    @Override
    public void showWait() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Hide progress bar
     */
    @Override
    public void removeWait() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }
}
