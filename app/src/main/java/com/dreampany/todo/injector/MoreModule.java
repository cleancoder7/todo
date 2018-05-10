package com.dreampany.todo.injector;

import com.dreampany.frame.injector.FragmentScoped;
import com.dreampany.todo.ui.fragment.MoreFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MoreModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract MoreFragment moreFragment();
}
