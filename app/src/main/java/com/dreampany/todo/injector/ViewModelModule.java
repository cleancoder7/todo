package com.dreampany.todo.injector;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.dreampany.frame.injector.ViewModelKey;
import com.dreampany.todo.factory.TaskViewModelFactory;
import com.dreampany.todo.vm.EditTaskViewModel;
import com.dreampany.todo.vm.TaskViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Hawladar Roman on 5/17/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TaskViewModel.class)
    abstract ViewModel bindTaskViewModel(TaskViewModel taskViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EditTaskViewModel.class)
    abstract ViewModel bindTaskViewModel(EditTaskModule editTaskModule);

    @Binds
    abstract ViewModelProvider.Factory bindTaskViewModelFactory(TaskViewModelFactory factory);
}
