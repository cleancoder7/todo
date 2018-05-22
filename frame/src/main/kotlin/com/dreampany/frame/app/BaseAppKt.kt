package com.dreampany.frame.app

import android.content.Context
import android.os.StrictMode
import android.support.multidex.MultiDex
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.dreampany.frame.BuildConfig
import dagger.android.support.DaggerApplication
import timber.log.Timber


/**
 * Created by Hawladar Roman on 5/22/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */
abstract class BaseAppKt : DaggerApplication() {

    open fun isDebug(): Boolean {
        return BuildConfig.DEBUG;
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        if (isDebug()) {
            setStrictMode()
        }
        super.onCreate()
        if (isDebug()) {
            Timber.plant(Timber.DebugTree())
        }
        TypefaceProvider.registerDefaultIconSets()
    }

    private fun setStrictMode() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build())

        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build())
    }
}