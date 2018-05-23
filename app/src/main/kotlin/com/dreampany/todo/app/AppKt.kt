package com.dreampany.todo.app

import com.dreampany.frame.app.BaseAppKt
import com.dreampany.todo.BuildConfig
import com.dreampany.todo.injector.DaggerAppComponentKt
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
        return DaggerAppComponentKt.builder().application(this).build()
    }
}