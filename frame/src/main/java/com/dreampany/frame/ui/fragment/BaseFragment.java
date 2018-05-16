package com.dreampany.frame.ui.fragment;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreampany.frame.data.model.Task;
import com.dreampany.frame.data.util.AndroidUtil;
import com.dreampany.frame.data.util.TextUtil;
import com.dreampany.frame.ui.activity.BaseActivity;

import java.io.Serializable;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment implements LifecycleOwner {

    protected ViewDataBinding binding;
    protected Task currentTask;
    protected View view;

    protected int getLayoutId() {
        return 0;
    }

    public boolean hasBackPressed() {
        return false;
    }

    protected abstract void onStartUi(Bundle state);

    protected abstract void onStopUi();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view != null) {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            return view;
        }

        int layoutId = getLayoutId();

        if (layoutId != 0) {
            binding = DataBindingUtil.inflate(inflater, layoutId, container, false);
            view = binding.getRoot();
        } else {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onStartUi(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        onStopUi();
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
        }
        super.onDestroyView();
    }

    @Override
    public Context getContext() {
        if (AndroidUtil.hasMarshmallow()) {
            return super.getContext();
        }
        View view = getView();
        if (view != null) {
            return view.getContext();
        }
        return getParent();
    }

    protected BaseActivity getParent() {
        Activity activity = getActivity();
        if (!BaseActivity.class.isInstance(activity) || activity.isFinishing() || activity.isDestroyed()) {
            return null;
        }
        return (BaseActivity) activity;
    }

    protected boolean isParentActive() {
        Activity activity = getParent();
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return false;
        }
        return true;
    }

    protected Task getCurrentTask(boolean freshTask) {
        if (currentTask == null || freshTask) {
            currentTask = getIntentValue(Task.class.getSimpleName());
        }
        return currentTask;
    }

    protected <T> T getIntentValue(String key) {
        Bundle bundle = getBundle();
        return getIntentValue(key, bundle);
    }

    protected <T> T getIntentValue(String key, Bundle bundle) {
        T t = null;
        if (bundle != null) {
            t = (T) bundle.getParcelable(key);
        }
        if (bundle != null && t == null) {
            t = (T) bundle.getSerializable(key);
        }
        return t;
    }

    protected Bundle getBundle() {
        return getArguments();
    }

    protected void setTitle(int resId) {
        if (resId <= 0) {
            return;
        }
        setTitle(TextUtil.getString(getContext(), resId));
    }

    protected void setSubtitle(int resId) {
        if (resId <= 0) {
            return;
        }
        setSubtitle(TextUtil.getString(getContext(), resId));
    }

    protected void setTitle(String title) {
        Activity activity = getActivity();
        if (BaseActivity.class.isInstance(activity)) {
            ((BaseActivity) activity).setTitle(title);
        }
    }

    protected void setSubtitle(String subtitle) {
        Activity activity = getActivity();
        if (BaseActivity.class.isInstance(activity)) {
            ((BaseActivity) activity).setSubtitle(subtitle);
        }
    }

    protected void openActivity(Class<?> clazz) {
        startActivity(new Intent(getParent(), clazz));
    }

    protected void openActivityForResult(Class<?> clazz, int requestCode) {
        startActivityForResult(new Intent(getParent(), clazz), requestCode);
    }

    protected void openActivityParcelable(Class<?> clazz, Task task) {
        Intent bundle = new Intent(getParent(), clazz);
        bundle.putExtra(Task.class.getSimpleName(), (Parcelable) task);
        startActivity(bundle);
    }

    protected void openActivitySerializable(Class<?> clazz, Task task) {
        Intent bundle = new Intent(getParent(), clazz);
        bundle.putExtra(Task.class.getSimpleName(), (Serializable) task);
        startActivity(bundle);
    }
}
