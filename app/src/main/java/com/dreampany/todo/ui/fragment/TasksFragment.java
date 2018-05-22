package com.dreampany.todo.ui.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dreampany.frame.data.model.Response;
import com.dreampany.frame.data.util.ViewUtil;
import com.dreampany.frame.injector.ActivityScoped;
import com.dreampany.frame.ui.fragment.BaseMenuFragment;
import com.dreampany.todo.R;
import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.databinding.FragmentTasksBinding;
import com.dreampany.todo.ui.activity.ToolsActivity;
import com.dreampany.todo.ui.adapter.TaskAdapter;
import com.dreampany.todo.ui.enums.UiSubtype;
import com.dreampany.todo.ui.enums.UiType;
import com.dreampany.todo.ui.model.TaskItem;
import com.dreampany.todo.ui.model.UiTask;
import com.dreampany.todo.vm.TasksViewModel;

import java.util.List;

import javax.inject.Inject;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.common.FlexibleItemDecoration;
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager;
import timber.log.Timber;

@ActivityScoped
public class TasksFragment extends BaseMenuFragment implements
        View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = TasksFragment.class.getSimpleName();

    private FragmentTasksBinding binding;
    private TaskAdapter adapter;
    private final int offset = 4;

    @Inject
    ViewModelProvider.Factory factory;
    @NonNull
    private TasksViewModel viewModel;

    @Inject
    public TasksFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tasks;
    }

    @Override
    protected int getMenuId() {
        return R.menu.menu_fragment_tasks;
    }

    @Override
    protected void onStartUi(Bundle state) {
        setTitle(R.string.home);
        initView();
        initRecycler();
        viewModel.loadTaskItems();
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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                viewModel.addNewTask();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //presenter.result(requestCode, resultCode);
    }

    @Override
    public void onRefresh() {

    }

    private void initView() {
        binding = (FragmentTasksBinding) super.binding;
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this, factory).get(TasksViewModel.class);
        binding.fab.setOnClickListener(this);
        ViewUtil.setSwipe(binding.swipeRefresh, this);
        viewModel.getAddNewTaskEvent().observe(this, this::openAddTaskUi);
        viewModel.getLiveResponse().observe(this, this::processResponse);
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

    private void openAddTaskUi(Void param) {
        openEditTaskUi(null);
    }

    private void openEditTaskUi(Task item) {
        UiTask<Task> task = new UiTask<>(false);
        task.setInput(item);
        task.setUiType(UiType.TASK);
        task.setSubtype(UiSubtype.EDIT);
        openActivityParcelable(ToolsActivity.class, task);
    }

    private void processResponse(Response<List<TaskItem>> response) {
        switch (response.status) {
            case READING:
                binding.stateful.showProgress();
                Timber.i("READING");
                break;

            case SUCCESS:
                binding.stateful.showContent();
                //renderDataState(response.data);
                Timber.i("SUCCESS");
                break;

            case ERROR:
                binding.stateful.showEmpty();
                //renderErrorState(response.error);
                Timber.i("ERROR");
                break;

            case EMPTY:
                binding.stateful.showEmpty();
                //renderErrorState(response.error);
                Timber.i("EMPTY");
                break;
        }
    }
}
