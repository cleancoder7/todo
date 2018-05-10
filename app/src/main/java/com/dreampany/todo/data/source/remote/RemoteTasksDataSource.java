package com.dreampany.todo.data.source.remote;

import android.support.annotation.NonNull;

import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.data.source.TasksDataSource;

import javax.inject.Singleton;

/**
 * Created by Hawladar Roman on 30/4/18.
 * Dreampany
 * dreampanymail@gmail.com
 */

@Singleton
public class RemoteTasksDataSource implements TasksDataSource {
    @Override
    public void loadTasks(@NonNull Callback callback) {

    }

    @Override
    public void loadTask(@NonNull String taskId, @NonNull Callback callback) {

    }

    @Override
    public void saveTask(@NonNull Task task) {

    }

    @Override
    public void completeTask(@NonNull Task task) {

    }

    @Override
    public void completeTask(@NonNull String taskId) {

    }

    @Override
    public void activateTask(@NonNull Task task) {

    }

    @Override
    public void activateTask(@NonNull String taskId) {

    }

    @Override
    public void clearCompletedTasks() {

    }

    @Override
    public void refreshTasks() {

    }

    @Override
    public void deleteAllTasks() {

    }

    @Override
    public void deleteTask(@NonNull String taskId) {

    }
}
