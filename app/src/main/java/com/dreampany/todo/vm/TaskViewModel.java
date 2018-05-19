package com.dreampany.todo.vm;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.dreampany.frame.rx.RxFacade;
import com.dreampany.todo.R;
import com.dreampany.todo.data.enums.Filter;
import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.data.source.TaskRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;


/**
 * Created by Hawladar Roman on 5/16/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */

public final class TaskViewModel extends AndroidViewModel {

    private static final String KEY_FILTER = "filter";
    @NonNull
    private final RxFacade facade;
    @NonNull
    private final TaskRepository taskRepository;

    @NonNull
    private final BehaviorSubject<Filter> filter;
    @NonNull
    private final BehaviorSubject<Boolean> loadingUi;
    @NonNull
    private final PublishSubject<Integer> snackbarMessage;


    @Inject
    public TaskViewModel(@NonNull Application application, @NonNull RxFacade facade, @NonNull TaskRepository taskRepository) {
        super(application);
        this.facade = facade;
        this.taskRepository = taskRepository;
        Timber.i("TaskRepository %s", taskRepository + "");

        filter = BehaviorSubject.createDefault(Filter.ALL);
        loadingUi = BehaviorSubject.createDefault(false);
        snackbarMessage = PublishSubject.create();
    }

/*    public void addNewTask() {
        mNavigator.addNewTask();
    }*/

    public void handleActivityResult(int requestCode, int resultCode) {
        // If a task was successfully added, show snackbar
        if (/*AddEditTaskActivity.REQUEST_ADD_TASK == requestCode &&*/ Activity.RESULT_OK == resultCode) {
            snackbarMessage.onNext(R.string.successfully_saved_task_message);
        }
    }

    @NonNull
    private Boolean shouldFilterTask(Task task, Filter filter) {
        switch (filter) {
            case ACTIVE:
                return task.isActive();
            case COMPLETED:
                return task.isCompleted();
            case ALL:
            default:
                return true;
        }
    }

    @StringRes
    public int getFilterRes(Filter filter) {
        switch (filter) {
            case ACTIVE:
                return R.string.label_active;
            case COMPLETED:
                return R.string.label_completed;
            case ALL:
            default:
                return R.string.label_all;
        }
    }

    public void setFilter(Filter filter) {
        this.filter.onNext(filter);
    }

    public void restoreState(@Nullable Bundle bundle) {
        if (bundle != null && bundle.containsKey(KEY_FILTER)) {
            Filter filter = (Filter) bundle.getSerializable(KEY_FILTER);
            this.filter.onNext(filter);
        }
    }

    @NonNull
    public Bundle saveState() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_FILTER, filter.getValue());
        return bundle;
    }

    @NonNull
    public Completable clearCompletedTasks() {
        return Completable.fromAction(this::clearCompletedTasksAndNotify);
    }

    @NonNull
    public Observable<Integer> getSnackbarMessage() {
        return snackbarMessage.serialize();
    }

    @NonNull
    public Observable<Boolean> getLoadingUi() {
        return loadingUi.serialize();
    }

    private void clearCompletedTasksAndNotify() {
        taskRepository.clearCompletedTasks();
        snackbarMessage.onNext(R.string.completed_tasks_cleared);
    }

}
