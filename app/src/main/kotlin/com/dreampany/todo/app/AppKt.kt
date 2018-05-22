package com.dreampany.todo.app

import com.dreampany.frame.BuildConfig
import com.dreampany.frame.app.BaseAppKt
import com.dreampany.todo.injector.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


/**
 * Created by Hawladar Roman on 5/22/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */
class AppKt : BaseAppKt() {

    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG;
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}