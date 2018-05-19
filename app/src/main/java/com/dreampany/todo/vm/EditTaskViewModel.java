package com.dreampany.todo.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.dreampany.frame.ld.SingleLiveEvent;
import com.dreampany.frame.rx.RxFacade;
import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.data.source.TaskRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
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
    private final SingleLiveEvent<Task> taskSavedEvent;

    @Inject
    public EditTaskViewModel(@NonNull Application application, @NonNull RxFacade facade, @NonNull TaskRepository taskRepository) {
        super(application);
        this.facade = facade;
        this.taskRepository = taskRepository;
        Timber.i("TaskRepository %s", taskRepository);
        taskSavedEvent = new SingleLiveEvent<>();
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean isNewTask() {
        return task == null;
    }

/*    @NonNull
    public Completable saveTask(String title, String description) {
        return createTask(title, description)
                .doOnCompleted(taskSavedEvent.setValue(task));
    }

    private Completable createTask(String title, String description) {
        Task newTask;
        if (isNewTask()) {
            newTask = new Task(title, description);
            if (newTask.isEmpty()) {
                showSnackbar(R.string.empty_task_message);
                return Completable.complete();
            }
        } else {
            newTask = new Task(title, description, mTaskId);
        }
        return mTasksRepository.saveTask(newTask);
    }*/
}
