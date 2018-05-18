package com.dreampany.todo.data.source;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dreampany.todo.data.model.Task;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public class TaskRepository implements TaskDataSource {

    private final TaskDataSource localDataSource;
    private final TaskDataSource remoteDataSource;

    private Map<String, Task> cachedTasks;
    private boolean cacheIsDirty = false;

    @Inject
    TaskRepository(@Local TaskDataSource localDataSource, @Remote TaskDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Single<List<Task>> loadTasks(/*@NonNull Callback callback*/) {
        //Preconditions.checkNotNull(callback);

        if (cachedTasks != null && !cacheIsDirty) {
            //callback.onLoad(new ArrayList<>(cachedTasks.values()));
            return Single.just(new ArrayList<>(cachedTasks.values()));
        }

        if (cacheIsDirty) {
            return loadTasksFromRemote();
        } else {
            return loadTasksFromLocal();
        }
    }

    @Override
    public Single<Task> loadTask(@NonNull String taskId) {
        return Single.just(new Task(""));
    }

    @Override
    public Completable saveTask(@NonNull Task task) {
        Preconditions.checkNotNull(task);
        remoteDataSource.saveTask(task);
        localDataSource.saveTask(task);

        if (cachedTasks == null) {
            cachedTasks = new LinkedHashMap<>();
        }
        cachedTasks.put(task.getId(), task);
        return Completable.complete();
    }

    @Override
    public Completable completeTask(@NonNull Task task) {
        Preconditions.checkNotNull(task);
        remoteDataSource.completeTask(task);
        localDataSource.completeTask(task);

        task.setCompleted(true);
        if (cachedTasks == null) {
            cachedTasks = new LinkedHashMap<>();
        }
        cachedTasks.put(task.getId(), task);
        return Completable.complete();
    }

    @Override
    public Completable completeTask(@NonNull String taskId) {
        Preconditions.checkNotNull(taskId);
        completeTask(getTaskById(taskId));
        return Completable.complete();
    }

    @Override
    public Completable activateTask(@NonNull Task task) {
        return Completable.complete();
    }

    @Override
    public Completable activateTask(@NonNull String taskId) {
        return Completable.complete();
    }

    @Override
    public Completable clearCompletedTasks() {
        return Completable.complete();
    }

    @Override
    public Completable refreshTasks() {
        return Completable.complete();
    }

    @Override
    public Completable deleteAllTasks() {
        return Completable.complete();
    }

    @Override
    public Completable deleteTask(@NonNull String taskId) {
        return Completable.complete();
    }

    private Single<List<Task>> loadTasksFromLocal(/*@NonNull final Callback callback*/) {
/*        localDataSource.loadTasks(new Callback() {
            @Override
            public void onLoad(List<Task> tasks) {
                refreshCache(tasks);
                callback.onLoad(new ArrayList<>(cachedTasks.values()));
            }

            @Override
            public void onLoad(Task task) {

            }

            @Override
            public void onEmpty() {
                loadTasksFromRemote(callback);
            }
        });*/
        return Single.just(new ArrayList<>());
    }

    private Single<List<Task>> loadTasksFromRemote(/*@NonNull final Callback callback*/) {
        return remoteDataSource.loadTasks();
    }

    private void refreshCache(List<Task> tasks) {
        if (cachedTasks == null) {
            cachedTasks = new LinkedHashMap<>();
        }
        cachedTasks.clear();
        for (Task task : tasks) {
            cachedTasks.put(task.getId(), task);
        }
        cacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Task> tasks) {
        localDataSource.deleteAllTasks();
        for (Task task : tasks) {
            localDataSource.saveTask(task);
        }
    }

    @Nullable
    private Task getTaskById(@NonNull String taskId) {
        Preconditions.checkNotNull(taskId);
        if (cachedTasks == null || cachedTasks.isEmpty()) {
            return null;
        } else {
            return cachedTasks.get(taskId);
        }
    }
}
