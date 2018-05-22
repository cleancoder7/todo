package com.dreampany.frame.app;

import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.beardedhen.androidbootstrap.TypefaceProvider;
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
        if (isDebug()) {
            setStrictMode();
        }
        super.onCreate();
        if (isDebug()) {
            Timber.plant(new Timber.DebugTree());
        }
        TypefaceProvider.registerDefaultIconSets();
    }

    private void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
}
