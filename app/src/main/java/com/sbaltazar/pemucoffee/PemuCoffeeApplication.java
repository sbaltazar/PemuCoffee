package com.sbaltazar.pemucoffee;

import android.app.Application;

import timber.log.Timber;

public class PemuCoffeeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Adding Timber logging
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
