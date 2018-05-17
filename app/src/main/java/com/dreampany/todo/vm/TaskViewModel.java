package com.dreampany.todo.vm;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.dreampany.todo.data.source.TaskRepository;


/**
 * Created by Hawladar Roman on 5/16/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */
public class TaskViewModel extends ViewModel {

    private TaskRepository repository;

    public TaskViewModel(@NonNull TaskRepository repository) {

    }
}
