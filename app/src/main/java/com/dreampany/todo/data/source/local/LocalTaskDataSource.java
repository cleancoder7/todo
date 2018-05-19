package com.dreampany.todo.data.source.local;

import android.support.annotation.NonNull;

import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.data.source.TaskDataSource;
import com.google.common.base.Preconditions;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class LocalTaskDataSource implements TaskDataSource {

    private final TaskDao taskDao;

    public LocalTaskDataSource(@NonNull TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @NonNull
    @Override
    public Observable<List<Task>> getTasks() {
        return taskDao.getTasks().toObservable();
    }

    @NonNull
    @Override
    public Observable<Task> getTask(@NonNull String taskId) {
        return taskDao.getTaskById(taskId).toObservable();
    }

    @NonNull
    @Override
    public Completable saveTask(@NonNull Task task) {
        Preconditions.checkNotNull(task);
        return Completable.fromAction(() -> {
            taskDao.insert(task);
        });
    }

    @NonNull
    @Override
    public Completable saveTasks(@NonNull List<Task> tasks) {
        Preconditions.checkNotNull(tasks);
        return Completable.fromAction(() -> {
            taskDao.insert(tasks);
        });
    }

    @NonNull
    @Override
    public Completable completeTask(@NonNull Task task) {
        Preconditions.checkNotNull(task);
        return completeTask(task.getId());
    }

    @NonNull
    @Override
    public Completable completeTask(@NonNull String taskId) {
        Preconditions.checkNotNull(taskId);
        return Completable.fromAction(() -> {
            taskDao.updateCompleted(taskId, true);
        });
    }

    @Override
    public Completable activateTask(@NonNull Task task) {
        Preconditions.checkNotNull(task);
        return activateTask(task.getId());
    }

    @Override
    public Completable activateTask(@NonNull String taskId) {
        Preconditions.checkNotNull(taskId);
        return Completable.fromAction(() -> {
            taskDao.updateCompleted(taskId, false);
        });
    }

    @NonNull
    @Override
    public Completable clearCompletedTasks() {
        return Completable.fromAction(taskDao::deleteCompleted);
    }

    @NonNull
    @Override
    public Completable refreshTasks() {
        return Completable.complete();
    }

    @NonNull
    @Override
    public Completable deleteAllTasks() {
        return Completable.fromAction(taskDao::deleteAll);
    }

    @NonNull
    @Override
    public Completable deleteTask(@NonNull String taskId) {
        Preconditions.checkNotNull(taskId);
        return Completable.fromAction(() -> {
            taskDao.deleteById(taskId);
        });
    }
}
