package com.android.testproject.amazingcanada.ui;

/**
 * Created by ankursharma on 3/7/18.
 */

public interface MainContract {

    interface Presenter {

    }

    interface View {
        void updateTitleBar(String titleText);
        void displayListOfItems();
        void showErrorDialog(int errorId);
        void showErrorDialog(String message);
        void showWait();
        void removeWait();
    }

}
