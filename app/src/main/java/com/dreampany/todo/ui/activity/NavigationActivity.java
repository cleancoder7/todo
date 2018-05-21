package com.dreampany.todo.ui.activity;

import android.os.Bundle;

import com.dreampany.frame.ui.activity.BaseBottomNavigationActivity;
import com.dreampany.todo.R;
import com.dreampany.todo.ui.fragment.TasksFragment;
import com.dreampany.todo.ui.fragment.MoreFragment;

import javax.inject.Inject;

import dagger.Lazy;

public class NavigationActivity extends BaseBottomNavigationActivity {

    @Inject
    Lazy<TasksFragment> tasksFragmentProvider;
    @Inject
    Lazy<MoreFragment> moreFragmentProvider;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected int getNavigationViewId() {
        return R.id.navigation_view;
    }

    @Override
    protected int getDefaultSelectedNavItemId() {
        return R.id.item_tasks;
    }

    @Override
    protected boolean isHomeUp() {
        return false;
    }

    @Override
    protected void onStartUi(Bundle state) {

    }

    @Override
    public void onBackPressed() {
        if (getCurrentFragment().hasBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onNavigationItem(int navItemId) {
        switch (navItemId) {
            case R.id.item_tasks:
                commitFragment(TasksFragment.class, tasksFragmentProvider, R.id.layout);
                break;
            case R.id.item_more:
                commitFragment(MoreFragment.class, moreFragmentProvider, R.id.layout);
                break;
        }
    }

    @Override
    protected void onStopUi() {

    }
}
