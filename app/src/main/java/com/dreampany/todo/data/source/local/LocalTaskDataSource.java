package com.dreampany.todo.data.source.local;

import android.support.annotation.NonNull;

import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.data.source.TaskDataSource;

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
        return null;
    }

    @NonNull
    @Override
    public Observable<Task> getTask(@NonNull String taskId) {
        return null;
    }

    @NonNull
    @Override
    public Completable saveTask(@NonNull Task task) {
        return null;
    }

    @NonNull
    @Override
    public Completable saveTasks(@NonNull List<Task> tasks) {
        return null;
    }

    @NonNull
    @Override
    public Completable completeTask(@NonNull Task task) {
        return null;
    }

    @NonNull
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

    @NonNull
    @Override
    public Completable clearCompletedTasks() {
        return null;
    }

    @NonNull
    @Override
    public Completable refreshTasks() {
        return null;
    }

    @NonNull
    @Override
    public Completable deleteAllTasks() {
        return null;
    }

    @NonNull
    @Override
    public Completable deleteTask(@NonNull String taskId) {
        return null;
    }

/*    @Override
    public Flowable<List<Task>> getTasks() {
        return taskDao.getTasks();
    }

    @Override
    public Flowable<Task> getTask(@NonNull final String taskId) {
        return taskDao.getTaskById(taskId);
    }*/

    /*@Override
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
    }*/
}
