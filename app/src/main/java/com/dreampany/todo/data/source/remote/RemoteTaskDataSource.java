package com.dreampany.todo.data.source.remote;

import android.support.annotation.NonNull;

import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.data.source.TaskDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Hawladar Roman on 30/4/18.
 * Dreampany
 * dreampanymail@gmail.com
 */

@Singleton
public class RemoteTaskDataSource implements TaskDataSource {

    public RemoteTaskDataSource() {
    }

    @Override
    public Single<List<Task>> getTasks() {
        return null;
    }

    @Override
    public Single<Task> getTask(@NonNull String taskId) {
        return null;
    }

    @Override
    public Completable saveTask(@NonNull Task task) {
        return null;
    }

    @Override
    public Completable completeTask(@NonNull Task task) {
        return null;
    }

    @Override
    public Completable completeTask(@NonNull String taskId) {
        return null;
    }

    @Override
    public Completable activateTask(@NonNull Task task) {
        return null;
    }

    @Override
    public Completable activateTask(@NonNull String taskId) {
        return null;
    }

    @Override
    public Completable clearCompletedTasks() {
        return null;
    }

    @Override
    public Completable refreshTasks() {
        return null;
    }

    @Override
    public Completable deleteAllTasks() {
        return null;
    }

    @Override
    public Completable deleteTask(@NonNull String taskId) {
        return null;
    }
}
