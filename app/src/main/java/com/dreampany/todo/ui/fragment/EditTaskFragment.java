package com.dreampany.todo.ui.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.dreampany.frame.injector.ActivityScoped;
import com.dreampany.frame.ui.fragment.BaseMenuFragment;
import com.dreampany.todo.R;
import com.dreampany.todo.databinding.FragmentEditTaskBinding;

import javax.inject.Inject;


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
        setTitle(R.string.title_home);
        initView();
    }

    @Override
    protected void onStopUi() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                doneTask();
                break;
        }
    }

    private void initView() {
        binding = (FragmentEditTaskBinding) super.binding;
        binding.fab.setOnClickListener(this);

    }

    private void doneTask() {
        //presenter.saveTask(binding.editTitle.getText().toString(), binding.editDescription.getText().toString());
    }
}
