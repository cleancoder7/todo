package com.dreampany.todo.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.dreampany.frame.ld.SingleLiveEvent;
import com.dreampany.frame.rx.RxFacade;
import com.dreampany.todo.R;
import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.data.source.TaskRepository;
import com.dreampany.todo.ui.model.EditTaskUiItem;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

/**
 * Created by Hawladar Roman on 5/19/2018.
 * Dreampany Ltd
 * dreampanymail@gmail.com
 */
public class EditTaskViewModel extends AndroidViewModel {

    @NonNull
    private final RxFacade facade;
    @NonNull
    private final TaskRepository taskRepository;

    private Task task;

    @NonNull
    private final PublishSubject<Integer> snackbarMessage;

    @NonNull
    private final SingleLiveEvent<Task> taskSavedEvent;

    @Inject
    public EditTaskViewModel(@NonNull Application application, @NonNull RxFacade facade, @NonNull TaskRepository taskRepository) {
        super(application);
        this.facade = facade;
        this.taskRepository = taskRepository;
        Timber.i("TaskRepository %s", taskRepository);
        taskSavedEvent = new SingleLiveEvent<>();
        snackbarMessage = PublishSubject.create();
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @NonNull
    public Observable<EditTaskUiItem> getUiItem() {
        if (isNewTask()) {
            return Observable.empty();
        }
        //return taskRepository.getTask(task.getId());
        return null;
    }

    @NonNull
    public Observable<Integer> getSnackbarText() {
        return snackbarMessage.hide();
    }

    private boolean isNewTask() {
        return task == null;
    }

    @NonNull
    public Completable saveTask(String title, String description) {
        return createTask(title, description)
                .doOnComplete(() -> taskSavedEvent.setValue(task));
    }

    private Completable createTask(String title, String description) {
        if (isNewTask()) {
            task = new Task(title, description);
            if (task.isEmpty()) {
                showSnackbar(R.string.empty_task_message);
                return Completable.complete();
            }
        } else {
            task.setTitle(title).setDescription(description);
            task.setTime(System.currentTimeMillis());
        }
        return taskRepository.saveTask(task);
    }

    private void showSnackbar(@StringRes int textId) {
        snackbarMessage.onNext(textId);
    }

}
