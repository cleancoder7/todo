package com.dreampany.todo.data.source;

import android.support.annotation.NonNull;

import com.dreampany.todo.data.model.Task;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface TaskDataSource {

/*    interface Callback {
        void onLoad(List<Task> tasks);

        void onLoad(Task task);

        void onEmpty();
    }*/

    Single<List<Task>> loadTasks(/*@NonNull Callback callback*/);

    Single<Task> loadTask(@NonNull String taskId/*, @NonNull Callback callback*/);

    Completable saveTask(@NonNull Task task);

    Completable completeTask(@NonNull Task task);

    Completable completeTask(@NonNull String taskId);

    Completable activateTask(@NonNull Task task);

    Completable activateTask(@NonNull String taskId);

    Completable clearCompletedTasks();

    Completable refreshTasks();

    Completable deleteAllTasks();

    Completable deleteTask(@NonNull String taskId);
}
