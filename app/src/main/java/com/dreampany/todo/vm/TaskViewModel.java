package com.dreampany.todo.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dreampany.frame.data.enums.Kind;
import com.dreampany.frame.data.model.Response;
import com.dreampany.frame.rx.RxFacade;
import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.data.source.TaskRepository;
import com.dreampany.todo.ui.model.TaskItem;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Hawladar Roman on 5/19/2018.
 * Dreampany Ltd
 * dreampanymail@gmail.com
 */
public class TaskViewModel extends AndroidViewModel {

    @NonNull
    private final RxFacade facade;
    @NonNull
    private final TaskRepository taskRepository;
    @NonNull
    private final CompositeDisposable disposables;
    private Task task;

    private final MutableLiveData<Response<TaskItem>> response;

    @Inject
    public TaskViewModel(@NonNull Application application, @NonNull RxFacade facade, @NonNull TaskRepository taskRepository) {
        super(application);
        this.facade = facade;
        this.taskRepository = taskRepository;
        disposables = new CompositeDisposable();
        //taskSavedEvent = new SingleLiveEvent<>();
        response = new MutableLiveData<>();
        Timber.i("TaskRepository %s", taskRepository);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @NonNull
    public MutableLiveData<Response<TaskItem>> getResponse() {
        return response;
    }

    public void loadTaskItem() {
        Disposable disposable = getTaskItem()
                .subscribeOn(facade.io())
                .observeOn(facade.ui())
                .subscribe(
                        item -> {
                            response.setValue(Response.success(Kind.READ, item));
                        }
                        , throwable -> Response.error(Kind.READ, throwable.getMessage()));
        disposables.add(disposable);
    }

    private Observable<TaskItem> getTaskItem() {
        if (isNewTask()) {
            return Observable.empty();
        }
        return taskRepository
                .getTask(task.getId())
                .map(this::restoreTask)
                .map(TaskItem::new);
    }

    private boolean isNewTask() {
        return task == null;
    }

    public void saveTask(String title, String description) {
        Completable completable = createTask(title, description);
        Disposable disposable = completable
                .subscribeOn(facade.io())
                .observeOn(facade.ui())
                .subscribe(
                        () -> {
                            response.setValue(Response.success(Kind.WRITE, null));
                        },
                        throwable -> {
                            response.setValue(Response.error(Kind.WRITE, throwable.getMessage()));
                        }
                );
        disposables.add(disposable);
    }

    private Task restoreTask(Task task) {
        String title = this.task.getTitle() != null ? this.task.getTitle() : task.getTitle();
        String description = this.task.getDescription() != null ? this.task.getDescription() : task.getDescription();
        return new Task(title, description);
    }

    private Completable createTask(String title, String description) {
        if (isNewTask()) {
            task = new Task(title, description);
            if (task.isEmpty()) {
                return Completable.complete();
            }
        } else {
            task.setTitle(title).setDescription(description);
            task.setTime(System.currentTimeMillis());
        }
        return taskRepository.saveTask(task);
    }
}
