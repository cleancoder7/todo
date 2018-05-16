package com.dreampany.todo.contract;

import com.dreampany.frame.presenter.BasePresenter;
import com.dreampany.frame.ui.view.BaseView;

/**
 * Created by Hawladar Roman on 2/5/18.
 * Dreampany
 * dreampanymail@gmail.com
 */
public interface EditTaskContract {

    interface View extends BaseView<Presenter> {
        boolean isActive();

        void onSuccess();

        void onError();
    }

    interface Presenter extends BasePresenter<View> {
        void saveTask(String title, String description);
    }

}
