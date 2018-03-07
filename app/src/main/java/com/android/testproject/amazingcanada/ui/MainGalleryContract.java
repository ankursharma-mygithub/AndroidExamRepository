package com.android.testproject.amazingcanada.ui;

import android.widget.ImageView;

/**
 * Created by ankursharma on 3/7/18.
 */

/**
 * Contract that all the parties associated with the Main/Launcher activity must implement.
 */
public interface MainGalleryContract {

    //Presenter layer contract
    interface Presenter {
        void getDataFromURL();
        void onBindItemAtPosition(RowItemHolder holder, int position);
        int getItemsCount();
        void onStop();
    }

    //View layer contract
    interface View {
        void updateTitleBar(String titleText);
        void displayListOfItems();
        void showErrorDialog(int errorId);
        void showErrorDialog(String message);
        void showWait();
        void removeWait();
    }

    //Contract for the view holder
    interface RowItemHolder {
        void updateTitle(String titleText);
        void updateDescription(String descText);
        ImageView getImageView();
    }

}
