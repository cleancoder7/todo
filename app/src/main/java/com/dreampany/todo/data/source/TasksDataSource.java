package com.dreampany.todo.data.source;

import android.support.annotation.NonNull;

import com.dreampany.todo.data.model.Task;

import java.util.List;

public interface TasksDataSource {

    interface Callback {
        void onLoad(List<Task> tasks);

        void onLoad(Task task);

        void onEmpty();
    }

    void loadTasks(@NonNull Callback callback);

    void loadTask(@NonNull String taskId, @NonNull Callback callback);

    void saveTask(@NonNull Task task);

    void completeTask(@NonNull Task task);

    void completeTask(@NonNull String taskId);

    void activateTask(@NonNull Task task);

    void activateTask(@NonNull String taskId);

    void clearCompletedTasks();

    void refreshTasks();

    void deleteAllTasks();

    void deleteTask(@NonNull String taskId);
}
