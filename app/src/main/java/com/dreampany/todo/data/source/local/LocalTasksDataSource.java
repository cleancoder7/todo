package com.dreampany.todo.data.source.local;

import android.support.annotation.NonNull;

import com.dreampany.frame.executor.AppExecutors;
import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.data.source.TasksDataSource;
import com.google.common.base.Preconditions;

import java.util.List;

import javax.inject.Singleton;

@Singleton
public class LocalTasksDataSource implements TasksDataSource {


    private final AppExecutors executors;
    private final TaskDao taskDao;


    public LocalTasksDataSource(@NonNull AppExecutors executors, @NonNull TaskDao taskDao) {
        this.executors = executors;
        this.taskDao = taskDao;
    }

    @Override
    public void loadTasks(@NonNull final Callback callback) {
        Runnable diskCommand = () -> {
            final List<Task> tasks = taskDao.getTasks();
            Runnable uiCommand = () -> {
                if (!tasks.isEmpty()) {
                    callback.onLoad(tasks);
                } else {
                    callback.onEmpty();
                }
            };
            executors.getMainThread().execute(uiCommand);
        };
        executors.getDiskIO().execute(diskCommand);
    }

    @Override
    public void loadTask(@NonNull final String taskId, @NonNull final Callback callback) {
        Runnable diskCommand = () -> {
            final Task task = taskDao.getTaskById(taskId);
            Runnable uiCommand = () -> {
                if (task != null) {
                    callback.onLoad(task);
                } else {
                    callback.onEmpty();
                }
            };
            executors.getMainThread().execute(uiCommand);
        };
        executors.getDiskIO().execute(diskCommand);
    }

    @Override
    public void saveTask(@NonNull final Task task) {
        Preconditions.checkNotNull(task);
        Runnable diskCommand = new Runnable() {
            @Override
            public void run() {
                taskDao.insert(task);
            }
        };
        executors.getDiskIO().execute(diskCommand);
    }

    @Override
    public void completeTask(@NonNull final Task task) {
        Preconditions.checkNotNull(task);
        Runnable diskCommand = () -> taskDao.updateCompleted(task.getId(), task.isCompleted());
        executors.getDiskIO().execute(diskCommand);
    }

    @Override
    public void completeTask(@NonNull String taskId) {

    }

    @Override
    public void activateTask(@NonNull final Task task) {
        Preconditions.checkNotNull(task);
        Runnable diskCommand = () -> taskDao.updateCompleted(task.getId(), false);
        executors.getDiskIO().execute(diskCommand);
    }

    @Override
    public void activateTask(@NonNull String taskId) {

    }

    @Override
    public void clearCompletedTasks() {
        Runnable diskCommand = () -> taskDao.deleteCompleted();
        executors.getDiskIO().execute(diskCommand);
    }

    @Override
    public void refreshTasks() {

    }

    @Override
    public void deleteAllTasks() {
        Runnable diskCommand = () -> taskDao.deleteAll();
        executors.getDiskIO().execute(diskCommand);
    }

    @Override
    public void deleteTask(@NonNull final String taskId) {
        Runnable diskCommand = () -> taskDao.deleteById(taskId);
        executors.getDiskIO().execute(diskCommand);
    }
}
