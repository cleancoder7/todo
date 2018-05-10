package com.dreampany.todo.presenter;

import com.dreampany.frame.injector.ActivityScoped;
import com.dreampany.todo.contract.TaskContract;
import com.dreampany.todo.data.enums.TaskFilterType;
import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.data.source.TasksDataSource;
import com.dreampany.todo.data.source.TasksRepository;
import com.google.android.gms.common.util.DataUtils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

@ActivityScoped
public final class TaskPresenter implements TaskContract.Presenter {

    private final TasksRepository tasksRepository;
    @Nullable
    private TaskContract.View taskView;
    private TaskFilterType filter;
    private boolean firstLoad;

    @Inject
    TaskPresenter(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
        filter = TaskFilterType.ALL;
        firstLoad = true;
    }

    @Override
    public void takeView(TaskContract.View view) {
        this.taskView = view;
        loadTasks(false);
    }

    @Override
    public void dropView() {
        taskView = null;
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadTasks(boolean forceUpdate) {

    }

    @Override
    public void addNewTask() {

    }

    @Override
    public TaskFilterType getFilter() {
        return filter;
    }

    @Override
    public void setFilter(TaskFilterType filter) {
        this.filter = filter;
    }

    private void loadTasks(boolean forceUpdate, boolean showLoadingUi) {
        if (showLoadingUi) {
            if (taskView != null) {
                taskView.showLoadIndicator(true);
            }
        }
        if (forceUpdate) {
            tasksRepository.refreshTasks();
        }

        TasksDataSource.Callback callback = new TasksDataSource.Callback() {
            @Override
            public void onLoad(List<Task> tasks) {
                List<Task> results = new ArrayList<>();
                for (Task task : tasks) {
                    switch (filter) {
                        case ALL:
                            results.add(task);
                            break;
                        case ACTIVE:
                            if (task.isActive()) {
                                results.add(task);
                            }
                            break;
                        case COMPLETED:
                            if (task.isCompleted()) {
                                results.add(task);
                            }
                            break;
                    }
                }

                //view may be unable to handle this updates
                if (taskView == null || !taskView.isActive()) {
                    return;
                }
                if (showLoadingUi) {
                    if (taskView != null) {
                        taskView.showLoadIndicator(false);
                    }
                }
                processTasks(results);
            }

            @Override
            public void onLoad(Task task) {

            }

            @Override
            public void onEmpty() {
                //view may be unable to handle this updates
                if (taskView == null || !taskView.isActive()) {
                    return;
                }
                taskView.showLoadingTasksError();
            }
        };
        tasksRepository.loadTasks(callback);
    }

    private void processTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            processEmptyTasks();
        } else {
            if (taskView != null) {
                taskView.showTasks(tasks);
            }
        }
    }

    private void processEmptyTasks() {
        if (taskView == null) {
            return;
        }
        switch (filter) {
            case ALL:
                taskView.showEmptyTasks();
                break;
            case ACTIVE:
                taskView.showEmptyActiveTasks();
                break;
            case COMPLETED:
                taskView.showEmptyCompletedTasks();
                break;
        }
    }
}
