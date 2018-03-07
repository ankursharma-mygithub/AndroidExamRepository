package com.android.testproject.amazingcanada.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ankursharma on 3/7/18.
 */

/**
 * Represents model class for the complete list of items.
 */
public class GalleryItemsList {
    @SerializedName("title")
    private String title;

    @SerializedName("rows")
    private List<GalleryItem> rows = null;

    public List<GalleryItem> getGalleryItems() {
        return rows;
    }

    public String getTitle() {
        return title;
    }

    public void setGalleryItems(List<GalleryItem> items) {
        rows = items;
    }
}
