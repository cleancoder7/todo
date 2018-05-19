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

import com.dreampany.frame.data.util.ViewUtil;
import com.dreampany.frame.injector.ActivityScoped;
import com.dreampany.frame.ui.fragment.BaseMenuFragment;
import com.dreampany.todo.R;
import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.databinding.FragmentHomeBinding;
import com.dreampany.todo.ui.activity.ToolsActivity;
import com.dreampany.todo.ui.adapter.TaskAdapter;
import com.dreampany.todo.ui.enums.UiSubtype;
import com.dreampany.todo.ui.enums.UiType;
import com.dreampany.todo.ui.model.TaskItem;
import com.dreampany.todo.ui.model.UiTask;
import com.dreampany.todo.vm.TaskViewModel;

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

    private FragmentHomeBinding binding;
    private TaskAdapter adapter;
    private final int offset = 4;

    @Inject
    ViewModelProvider.Factory factory;
    @NonNull
    private TaskViewModel taskViewModel;

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
        taskViewModel = ViewModelProviders.of(this, factory).get(TaskViewModel.class);
        Timber.i("TaskViewModel " + taskViewModel);
        Timber.i("Application " + taskViewModel.getApplication());
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
        //presenter.result(requestCode, resultCode);
    }

    @Override
    public void onRefresh() {

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
