package com.dreampany.todo.injector;

import com.dreampany.frame.injector.FragmentScoped;
import com.dreampany.todo.ui.fragment.TasksFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TasksModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract TasksFragment taskFragment();
}
