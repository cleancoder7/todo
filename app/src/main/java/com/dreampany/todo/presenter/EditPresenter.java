package com.dreampany.todo.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dreampany.todo.contract.EditTaskContract;
import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.data.source.TasksRepository;
import com.google.common.base.Strings;

import java.util.UUID;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by Hawladar Roman on 2/5/18.
 * Dreampany
 * dreampanymail@gmail.com
 */
public final class EditPresenter implements EditTaskContract.Presenter {

    @Nullable
    private EditTaskContract.View view;
    @NonNull
    TasksRepository tasksRepository;
    @Nullable
    String taskId;
    Lazy<Boolean> shouldLoadDataFromRepo;

    @Inject
    EditPresenter(TasksRepository tasksRepository, String taskId, Lazy<Boolean> shouldLoadDataFromRepo) {
        this.tasksRepository = tasksRepository;
        this.taskId = taskId;
        this.shouldLoadDataFromRepo = shouldLoadDataFromRepo;
    }


    @Override
    public void saveTask(String title, String description) {
        if (isNewTask()) {
            createTask(title, description);
        } else {
            updateTask(title, description);
        }
    }

    @Override
    public void takeView(EditTaskContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }

    private boolean isNewTask() {
        return taskId == null;
    }

    private void createTask(String title, String description) {
        Task task = new Task(UUID.randomUUID().toString(), title, description);
        if (task.isEmpty()) {
            if (view != null) {
                view.onError();
            }
        } else {
            tasksRepository.saveTask(task);
            if (view != null) {
                view.onSuccess();
            }
        }
    }

    private void updateTask(String title, String description) {
        tasksRepository.saveTask(new Task(taskId, title, description));
        if (view != null) {
            view.onSuccess();
        }
    }
}
