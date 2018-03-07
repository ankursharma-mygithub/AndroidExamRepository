package com.android.testproject.amazingcanada.network;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.android.testproject.amazingcanada.common.MyApplication;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * Created by ankursharma on 3/7/18.
 */

/**
 * Image downloader which uses Picasso library.
 */
public class AppPicassoImageDownloader implements ImageDownloader {

    @Override
    public void downloadImage(Context context, String url, final ImageView imageView) {
        imageView.setImageResource(android.R.drawable.ic_dialog_info);
        OkHttpClient client = new OkHttpClient();

        /* USE "PICASSO" library to download image from given URL into an imageView and PICASSO takes care of caching also.
        /*
        By default Picasso uses UrlConnectionDownloader and HttpURLConnection which won't automatically
        redirect from HTTP to HTTPS (or vice versa).
        So here for now using the OkHttp3Downloader which redirects from HTTP to HTTPS or vice-versa.
        BUT IN PRODUCTION CODE, NEVER USE THIS REDIRECTION AS DOING SO AMY HAVE SERIOUS SECURITY CONSEQUENCES.
         */
        Picasso picasso = new Picasso.Builder(MyApplication.getMyApp().getApplicationContext())
                .downloader(new OkHttp3Downloader(client))
                .build();
        picasso.load(url).noFade().into(imageView, new com.squareup.picasso.Callback.EmptyCallback() {
            @Override
            public void onError() {
                super.onError();
                imageView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSuccess() {
                super.onSuccess();
                imageView.setVisibility(View.VISIBLE);
            }
        });
    }
}
