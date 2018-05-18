package com.dreampany.todo.data.source.local;

import android.support.annotation.NonNull;

import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.data.source.TaskDataSource;
import com.google.common.base.Preconditions;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public class LocalTaskDataSource implements TaskDataSource {

    private final TaskDao taskDao;

    public LocalTaskDataSource(@NonNull TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public Single<List<Task>> getTasks() {
        return Single.create(emitter -> {
            List<Task> tasks = taskDao.getTasks();
            if (!emitter.isDisposed()) {
                emitter.onSuccess(tasks);
            }
        });
    }

    @Override
    public Single<Task> getTask(@NonNull final String taskId) {
        return Single.create(emitter -> {
            Task task = taskDao.getTaskById(taskId);
            if (!emitter.isDisposed()) {
                emitter.onSuccess(task);
            }
        });
    }

    @Override
    public Completable saveTask(@NonNull final Task task) {
        Preconditions.checkNotNull(task);
        return Completable.create(emitter -> {
            taskDao.insert(task);
            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        });
    }

    @Override
    public Completable completeTask(@NonNull final Task task) {
        Preconditions.checkNotNull(task);
        return Completable.create(emitter -> {
            taskDao.updateCompleted(task.getId(), task.isCompleted());
            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        });
    }

    @Override
    public Completable completeTask(@NonNull String taskId) {
        return Completable.complete();
    }

    @Override
    public Completable activateTask(@NonNull final Task task) {
        Preconditions.checkNotNull(task);
        return Completable.create(emitter -> {
            taskDao.updateCompleted(task.getId(), false);
            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        });
    }

    @Override
    public Completable activateTask(@NonNull String taskId) {
        return Completable.complete();
    }

    @Override
    public Completable clearCompletedTasks() {
        return Completable.create(emitter -> {
            taskDao.deleteCompleted();
            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        });
    }

    @Override
    public Completable refreshTasks() {
        return Completable.complete();
    }

    @Override
    public Completable deleteAllTasks() {
        return Completable.create(emitter -> {
            taskDao.deleteAll();
            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        });
    }

    @Override
    public Completable deleteTask(@NonNull final String taskId) {
        return Completable.create(emitter -> {
            taskDao.deleteById(taskId);
            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        });
    }
}
