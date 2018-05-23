package com.dreampany.todo.injector

import com.dreampany.frame.injector.ActivityScoped
import com.dreampany.todo.ui.activity.LaunchActivityKt
import com.dreampany.todo.ui.activity.NavigationActivity
import com.dreampany.todo.ui.activity.ToolsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by Hawladar Roman on 5/23/2018.
 * Dreampany Ltd
 * dreampanymail@gmail.com
 */
@Module
internal abstract class ActivityModuleKt {
    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun launchActivity(): LaunchActivityKt

    @ActivityScoped
    @ContributesAndroidInjector(modules = [TasksModule::class, MoreModule::class])
    internal abstract fun navigationActivity(): NavigationActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf(EditTaskModule::class, TaskModule::class))
    internal abstract fun toolsActivity(): ToolsActivity
}