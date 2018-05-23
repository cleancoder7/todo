package com.dreampany.todo.injector

import android.app.Application
import com.dreampany.frame.injector.AppModule
import com.dreampany.todo.app.AppKt
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * Created by Hawladar Roman on 5/23/2018.
 * Dreampany Ltd
 * dreampanymail@gmail.com
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityModuleKt::class,
    BuildersModule::class
])
interface AppComponentKt : AndroidInjector<AppKt> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponentKt
    }
}