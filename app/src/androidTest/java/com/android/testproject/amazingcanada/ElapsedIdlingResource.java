package com.android.testproject.amazingcanada;

import android.support.test.espresso.IdlingResource;

/**
 * Created by ankursharma on 3/7/18.
 */

public class ElapsedIdlingResource implements IdlingResource {

    private final long startingTime;
    private final long waitTime;
    private ResourceCallback resourceCallback;

    public ElapsedIdlingResource(long waitTime) {
        this.startingTime = System.currentTimeMillis();
        this.waitTime = waitTime;
    }

    @Override
    public String getName() {
        return ElapsedIdlingResource.class.getName() + ":" + waitTime;
    }

    @Override
    public boolean isIdleNow() {
        long elapsed = System.currentTimeMillis() - startingTime;
        boolean idle = (elapsed >= waitTime);
        if (idle) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }
}
