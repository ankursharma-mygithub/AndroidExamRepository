package com.android.testproject.amazingcanada.network;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by ankursharma on 3/7/18.
 */

public interface ImageDownloaderContract {
    void downloadImage(Context context, String url, ImageView imageView);
}
