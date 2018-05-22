package com.dreampany.todo.vm;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dreampany.frame.data.enums.Kind;
import com.dreampany.frame.data.model.Response;
import com.dreampany.frame.rx.RxFacade;
import com.dreampany.frame.vm.BaseViewModel;
import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.data.source.TaskRepository;
import com.dreampany.todo.ui.model.TaskItem;
import com.dreampany.todo.ui.model.UiTask;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Hawladar Roman on 5/19/2018.
 * Dreampany Ltd
 * dreampanymail@gmail.com
 */
public class EditTaskViewModel extends BaseViewModel<UiTask<Task>> {

    @NonNull
    private final TaskRepository taskRepository;
    @NonNull
    private final MutableLiveData<Response<TaskItem>> liveResponse;

    @Inject
    EditTaskViewModel(@NonNull Application application, @NonNull RxFacade facade, @NonNull TaskRepository taskRepository) {
        super(application, facade);
        this.taskRepository = taskRepository;
        liveResponse = new MutableLiveData<>();
        Timber.i("TaskRepository %s", taskRepository);
    }

    @NonNull
    public MutableLiveData<Response<TaskItem>> getLiveResponse() {
        return liveResponse;
    }

    public void loadTaskItem() {
        Disposable disposable = getTaskItem()
                .subscribeOn(facade.io())
                .observeOn(facade.ui())
                .subscribe(
                        item -> {
                            liveResponse.setValue(Response.success(Kind.READ, item));
                        }
                        , throwable -> Response.error(Kind.READ, throwable.getMessage()));

        addSubscription(disposable);
    }

    private Observable<TaskItem> getTaskItem() {
        if (hasTask()) {
            return taskRepository
                    .getTask(getT().getInput().getId())
                    .map(this::restoreTask)
                    .map(TaskItem::new);
        }
        return Observable.empty();
    }

    private boolean hasTask() {
        Task task = getTask();
        return (task != null);
    }

    public void saveTask(String title, String description) {
        Completable completable = createTask(title, description);
        Disposable disposable = completable
                .subscribeOn(facade.io())
                .observeOn(facade.ui())
                .subscribe(
                        () -> {
                            liveResponse.setValue(Response.success(Kind.WRITE, null));
                        },
                        throwable -> {
                            liveResponse.setValue(Response.error(Kind.WRITE, throwable.getMessage()));
                        }
                );
        disposables.add(disposable);
    }

    private Task restoreTask(Task task) {
        String title = null, description = null;
        Task currentTask = getTask();
        if (currentTask != null) {
            if (currentTask.getTitle() != null) {
                title = currentTask.getTitle();
            }
            if (currentTask.getDescription() != null) {
                description = currentTask.getDescription();
            }
        }
        if (title != null) {
            title = task.getTitle();
        }
        if (description != null) {
            description = task.getDescription();
        }
        return new Task(title, description);
    }

    private Completable createTask(String title, String description) {
/*        if (hasTask()) {
            Task task = new Task(title, description);
            getT().setInput(task);
            if (task.isEmpty()) {
                return Completable.complete();
            }
        } else {
            task.setTitle(title).setDescription(description);
            task.setTime(System.currentTimeMillis());
        }
        return taskRepository.saveTask(task);*/
        return Completable.complete();
    }

    private Task getTask() {
        UiTask<Task> uiTask = getT();
        if (uiTask != null) {
            return uiTask.getInput();
        }
        return null;
    }
}
