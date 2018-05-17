package com.dreampany.todo.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.dreampany.frame.rx.RxFacade;
import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.data.source.TaskDataSource;
import com.dreampany.todo.data.source.TaskRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Hawladar Roman on 5/16/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */

public class TaskViewModel extends AndroidViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private RxFacade facade;
    private TaskRepository taskRepository;

    @Inject
    public TaskViewModel(@NonNull Application application, @NonNull RxFacade facade, @NonNull TaskRepository taskRepository) {
        super(application);
        this.facade = facade;
        this.taskRepository = taskRepository;
    }

    @Override
    protected void onCleared() {
       disposable.clear();

       taskRepository.loadTasks(new TaskDataSource.Callback() {
           @Override
           public void onLoad(List<Task> tasks) {

           }

           @Override
           public void onLoad(Task task) {

           }

           @Override
           public void onEmpty() {

           }
       });
    }


}
