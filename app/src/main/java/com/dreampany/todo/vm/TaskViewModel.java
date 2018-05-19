package com.dreampany.todo.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.dreampany.frame.rx.RxFacade;
import com.dreampany.todo.data.enums.Filter;
import com.dreampany.todo.data.source.TaskRepository;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;


/**
 * Created by Hawladar Roman on 5/16/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */

public final class TaskViewModel extends AndroidViewModel {

    @NonNull
    private final RxFacade facade;
    @NonNull
    private final TaskRepository taskRepository;

    @NonNull
    private final BehaviorSubject<Filter> filter;
    @NonNull
    private final BehaviorSubject<Boolean> loadingUi;
    @NonNull
    private final PublishSubject<Integer> snackbarText;


    @Inject
    public TaskViewModel(@NonNull Application application, @NonNull RxFacade facade, @NonNull TaskRepository taskRepository) {
        super(application);
        this.facade = facade;
        this.taskRepository = taskRepository;
        Timber.i("TaskRepository %s", taskRepository + "");

        filter = BehaviorSubject.createDefault(Filter.ALL);
        loadingUi = BehaviorSubject.createDefault(false);
        snackbarText = PublishSubject.create();
    }

}
