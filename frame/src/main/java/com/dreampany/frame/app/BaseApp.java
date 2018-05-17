package com.dreampany.frame.app;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.dreampany.frame.BuildConfig;

import dagger.android.support.DaggerApplication;
import timber.log.Timber;

public abstract class BaseApp extends DaggerApplication {

    protected boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (isDebug()) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
