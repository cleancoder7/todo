package com.dreampany.todo.ui.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.View;

import com.dreampany.frame.data.model.Response;
import com.dreampany.frame.injector.ActivityScoped;
import com.dreampany.frame.ui.fragment.BaseMenuFragment;
import com.dreampany.todo.R;
import com.dreampany.todo.data.model.Task;
import com.dreampany.todo.databinding.FragmentEditTaskBinding;
import com.dreampany.todo.ui.model.TaskItem;
import com.dreampany.todo.ui.model.UiTask;
import com.dreampany.todo.vm.EditTaskViewModel;

import javax.inject.Inject;

import timber.log.Timber;


/**
 * Created by Hawladar Roman on 1/5/18.
 * Dreampany Ltd
 * dreampanymail@gmail.com
 */

@ActivityScoped
public class EditTaskFragment extends BaseMenuFragment
        implements View.OnClickListener {

    private FragmentEditTaskBinding binding;
    @Inject
    ViewModelProvider.Factory factory;
    @NonNull
    private EditTaskViewModel viewModel;

    @Inject
    public EditTaskFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_task;
    }

    @Override
    protected void onMenuCreated(Menu menu) {

    }

    @Override
    protected void onStartUi(Bundle state) {
        initView();
        UiTask<Task> uiTask = getCurrentTask(true);
        viewModel.setT(uiTask);
        viewModel.loadTitle();
        viewModel.loadTaskItem();
    }

    @Override
    protected void onStopUi() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                saveTask();
                break;
        }
    }

    private void initView() {
        binding = (FragmentEditTaskBinding) super.binding;
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this, factory).get(EditTaskViewModel.class);
        Timber.i("EditTaskViewModel - %s", viewModel);
        binding.fab.setOnClickListener(this);

        viewModel.getLiveTitle().observe(this, this::processTitle);
        viewModel.getLiveResponse().observe(this, this::processResponse);
    }

    private void saveTask() {
        viewModel.saveTask(binding.editTitle.getText().toString(), binding.editDescription.getText().toString());
    }

    private void processTitle(String title) {
        setTitle(title);
    }

    private void processResponse(Response<TaskItem> response) {
        switch (response.status) {
            case READING:
                binding.stateful.showProgress();
                Timber.i("READING");
                break;

            case SUCCESS:
                binding.stateful.showContent();
                Timber.i("SUCCESS");
                break;

            case ERROR:
                binding.stateful.showEmpty();
                Timber.i("ERROR");
                break;

            case EMPTY:
                binding.stateful.showEmpty();
                Timber.i("EMPTY");
                break;
        }
    }

    private void updateUi(TaskItem item) {
        binding.editTitle.setText(item.getItem().getTitle());
        binding.editDescription.setText(item.getItem().getDescription());
    }

    private void showSnackbar(@StringRes int textId) {
        Snackbar.make(binding.editTitle, textId, Snackbar.LENGTH_LONG).show();
    }

}
