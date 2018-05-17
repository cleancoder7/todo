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
    public void loadTasks(@NonNull Callback callback) {
        Preconditions.checkNotNull(callback);

        if (cachedTasks != null && !cacheIsDirty) {
            callback.onLoad(new ArrayList<>(cachedTasks.values()));
        }

        if (cacheIsDirty) {
            loadTasksFromRemote(callback);
        } else {
            loadTasksFromLocal(callback);
        }
    }

    @Override
    public void loadTask(@NonNull String taskId, @NonNull Callback callback) {

    }

    @Override
    public void saveTask(@NonNull Task task) {
        Preconditions.checkNotNull(task);
        remoteDataSource.saveTask(task);
        localDataSource.saveTask(task);

        if (cachedTasks == null) {
            cachedTasks = new LinkedHashMap<>();
        }
        cachedTasks.put(task.getId(), task);
    }

    @Override
    public void completeTask(@NonNull Task task) {
        Preconditions.checkNotNull(task);
        remoteDataSource.completeTask(task);
        localDataSource.completeTask(task);

        task.setCompleted(true);
        if (cachedTasks == null) {
            cachedTasks = new LinkedHashMap<>();
        }
        cachedTasks.put(task.getId(), task);
    }

    @Override
    public void completeTask(@NonNull String taskId) {
        Preconditions.checkNotNull(taskId);
        completeTask(getTaskById(taskId));
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

    private void loadTasksFromLocal(@NonNull final Callback callback) {
        localDataSource.loadTasks(new Callback() {
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
        });
    }

    private void loadTasksFromRemote(@NonNull final Callback callback) {
        remoteDataSource.loadTasks(new Callback() {
            @Override
            public void onLoad(List<Task> tasks) {
                refreshCache(tasks);
            }

            @Override
            public void onLoad(Task task) {

            }

            @Override
            public void onEmpty() {

            }
        });
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
