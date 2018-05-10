package com.dreampany.todo.ui.fragment;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dreampany.frame.data.util.ViewUtil;
import com.dreampany.frame.injector.ActivityScoped;
import com.dreampany.frame.ui.fragment.BaseMenuFragment;
import com.dreampany.todo.R;
import com.dreampany.todo.contract.TaskContract;
import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.databinding.FragmentHomeBinding;
import com.dreampany.todo.presenter.TaskPresenter;
import com.dreampany.todo.ui.activity.ToolsActivity;
import com.dreampany.todo.ui.adapter.TaskAdapter;
import com.dreampany.todo.ui.enums.UiSubtype;
import com.dreampany.todo.ui.enums.UiType;
import com.dreampany.todo.ui.model.TaskItem;
import com.dreampany.todo.ui.model.UiTask;

import java.util.List;

import javax.inject.Inject;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.common.FlexibleItemDecoration;
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager;

@ActivityScoped
public class TasksFragment extends BaseMenuFragment implements
        View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        TaskContract.View {

    @Inject
    TaskPresenter presenter;
    private FragmentHomeBinding binding;
    private TaskAdapter adapter;
    private final int offset = 4;

    @Inject
    public TasksFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected int getMenuId() {
        return R.menu.menu_fragment_home;
    }

    @Override
    protected void onStartUi(Bundle state) {
        setTitle(R.string.title_home);
        presenter.takeView(this);
        initView();
        initRecycler();
    }

    @Override
    protected void onMenuCreated(Menu menu) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_filter:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStopUi() {
        presenter.dropView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                openAddTaskUi();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.result(requestCode, resultCode);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showLoadIndicator(boolean active) {

    }

    @Override
    public void showLoadingTasksError() {

    }

    @Override
    public void showTasks(List<Task> tasks) {

    }

    @Override
    public void showTaskDetailsUi(String taskId) {

    }

    @Override
    public void showTaskMarkedComplete() {

    }

    @Override
    public void showTaskMarkedActive() {

    }

    @Override
    public void showCompletedTasksCleared() {

    }

    @Override
    public void showEmptyTasks() {

    }

    @Override
    public void showEmptyActiveTasks() {

    }

    @Override
    public void showEmptyCompletedTasks() {

    }

    @Override
    public void showSuccessfullySavedMessage() {

    }

    private void initView() {
        binding = (FragmentHomeBinding) super.binding;
        binding.fab.setOnClickListener(this);
        ViewUtil.setSwipe(binding.swipeRefresh, this);
    }

    private void initRecycler() {
        binding.setItems(new ObservableArrayList<>());
        adapter = new TaskAdapter((FlexibleAdapter.OnItemClickListener) (view, position) -> {
            if (position != RecyclerView.NO_POSITION) {
                TaskItem item = adapter.getItem(position);
                openEditTaskUi(item.getItem());
                return true;
            }
            return false;
        });

        ViewUtil.setRecycler(
                binding.recyclerView,
                adapter,
                new SmoothScrollLinearLayoutManager(getContext()),
                null,
                new FlexibleItemDecoration(getContext())
                        .addItemViewType(R.layout.item_task, offset)
                        .withEdge(true)
        );
    }

    private void openAddTaskUi() {
        UiTask<Task> task = new UiTask<>(false);
        task.setUiType(UiType.TASK);
        task.setSubtype(UiSubtype.EDIT);
        openActivityParcelable(ToolsActivity.class, task);
    }

    private void openEditTaskUi(Task item) {
        UiTask<Task> task = new UiTask<>(false);
        task.setInput(item);
        task.setUiType(UiType.TASK);
        task.setSubtype(UiSubtype.EDIT);
        openActivityParcelable(ToolsActivity.class, task);
    }

}
