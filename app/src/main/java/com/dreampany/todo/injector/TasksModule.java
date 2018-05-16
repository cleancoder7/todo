package com.dreampany.todo.injector;

import com.dreampany.frame.injector.ActivityScoped;
import com.dreampany.frame.injector.FragmentScoped;
import com.dreampany.todo.contract.TaskContract;
import com.dreampany.todo.presenter.TaskPresenter;
import com.dreampany.todo.ui.fragment.TasksFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TasksModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract TasksFragment taskFragment();

    @FragmentScoped
    @Binds
    abstract TaskContract.Presenter taskPresenter(TaskPresenter presenter);
}
