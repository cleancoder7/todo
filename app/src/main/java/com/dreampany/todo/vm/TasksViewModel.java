package com.dreampany.todo.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.dreampany.todo.data.source.TasksRepository;


/**
 * Created by Hawladar Roman on 5/16/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */
public class TasksViewModel extends AndroidViewModel {

    private TasksRepository repository;

    public TasksViewModel(@NonNull Application application) {
        super(application);
    }
}
