package com.dreampany.todo.contract;

import com.dreampany.frame.presenter.BasePresenter;
import com.dreampany.frame.ui.view.BaseView;
import com.dreampany.todo.data.enums.TaskFilterType;
import com.dreampany.todo.data.model.Task;

import java.util.List;

public interface TaskContract {

    interface View extends BaseView<Presenter> {

        void showLoadIndicator(boolean active);

        void showLoadingTasksError();

        void showTasks(List<Task> tasks);

        void showTaskDetailsUi(String taskId);

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        void showCompletedTasksCleared();

        void showEmptyTasks();

        void showEmptyActiveTasks();

        void showEmptyCompletedTasks();

        void showSuccessfullySavedMessage();
    }

    interface Presenter extends BasePresenter<View> {
        void result(int requestCode, int resultCode);

        void loadTasks(boolean forceUpdate);

        void addNewTask();

        TaskFilterType getFilter();

        void setFilter(TaskFilterType filter);
    }
}
