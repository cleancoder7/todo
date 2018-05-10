package com.dreampany.frame.app;

import android.content.Context;
import android.support.multidex.MultiDex;

import dagger.android.support.DaggerApplication;

public abstract class BaseApp extends DaggerApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }
}
