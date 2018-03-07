package com.android.testproject.amazingcanada.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ankursharma on 3/7/18.
 */

/**
 * Represents each individual item in the list.
 */
public class GalleryItem {

    //Title of the row information
    @SerializedName("title")
    private String mTitle;
    //Description
    @SerializedName("description")
    private String mDescription;
    //Image hyperlink
    @SerializedName("imageHref")
    private String mImageHref;

    public GalleryItem(String title, String description, String imageHref) {
        this.mTitle = title;
        this.mDescription = description;
        this.mImageHref = imageHref;
    }

    //Getter and setter functions
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getImageUrl() {
        return mImageHref;
    }

    public void setImageUrl(String imageUrl) {
        mImageHref = imageUrl;
    }

}
