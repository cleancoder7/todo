package com.dreampany.frame.presenter;

import com.dreampany.frame.ui.view.BaseView;

public interface BasePresenter<T extends BaseView> {
    void takeView(T view);

    void dropView();
}
