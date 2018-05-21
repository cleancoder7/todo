package com.dreampany.todo.injector;

import com.dreampany.frame.injector.ActivityScoped;
import com.dreampany.todo.ui.activity.LaunchActivity;
import com.dreampany.todo.ui.activity.NavigationActivity;
import com.dreampany.todo.ui.activity.ToolsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityModule {
    @ActivityScoped
    @ContributesAndroidInjector
    abstract LaunchActivity launchActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {TasksModule.class, MoreModule.class})
    abstract NavigationActivity navigationActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {EditTaskModule.class, TaskModule.class})
    abstract ToolsActivity toolsActivity();
}
