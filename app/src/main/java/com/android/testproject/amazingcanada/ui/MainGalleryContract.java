package com.android.testproject.amazingcanada.ui;

import android.widget.ImageView;

/**
 * Created by ankursharma on 3/7/18.
 */

public interface MainGalleryContract {

    interface Presenter {
        void getDataFromURL();
        void onBindItemAtPosition(RowItemHolder holder, int position);
        int getItemsCount();
        void onStop();
    }

    interface View {
        void updateTitleBar(String titleText);
        void displayListOfItems();
        void showErrorDialog(int errorId);
        void showErrorDialog(String message);
        void showWait();
        void removeWait();
    }

    interface RowItemHolder {
        void updateTitle(String titleText);
        void updateDescription(String descText);
        ImageView getImageView();
    }

}
