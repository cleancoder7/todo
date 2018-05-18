package com.dreampany.todo.data.source;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dreampany.todo.data.model.Task;
import com.google.common.base.Preconditions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class TaskRepository implements TaskDataSource {

    private final TaskDataSource localDataSource;
    private final TaskDataSource remoteDataSource;

    private Map<String, Task> cachedTasks;
    private boolean cacheIsDirty = false;

    @Inject
    public TaskRepository(@Local TaskDataSource localDataSource, @Remote TaskDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<List<Task>> getTasks() {
        return null;
    }

    @Override
    public Observable<Task> getTask(@NonNull String taskId) {
        return null;
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

    @NonNull
    @Override
    public Completable saveTasks(@NonNull List<Task> tasks) {
        return null;
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

    private Flowable<List<Task>> loadTasksFromLocal() {
/*        localDataSource.getTasks(new Callback() {
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
        return null;
    }

    private Single<List<Task>> loadTasksFromRemote(/*@NonNull final Callback callback*/) {
        return null;//remoteDataSource.getTasks();
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
