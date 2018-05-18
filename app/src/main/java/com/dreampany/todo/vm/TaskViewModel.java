package com.dreampany.todo.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.dreampany.frame.rx.RxFacade;
import com.dreampany.todo.data.enums.Filter;
import com.dreampany.todo.data.source.TaskRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;


/**
 * Created by Hawladar Roman on 5/16/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */

public class TaskViewModel extends AndroidViewModel {

    private RxFacade facade;
    private TaskRepository taskRepository;
    private Filter filter;

    private final CompositeDisposable disposable = new CompositeDisposable(); //to support rx facade
    public final ObservableField<String> filterLabel = new ObservableField<>();
    public final ObservableBoolean loadingUi = new ObservableBoolean(false);

    @Inject
    public TaskViewModel(@NonNull Application application, @NonNull RxFacade facade, @NonNull TaskRepository taskRepository) {
        super(application);
        this.facade = facade;
        this.taskRepository = taskRepository;
        Timber.i("TaskRepository %s", taskRepository+"");
        setFiltering(Filter.ALL);
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    public void start() {
        loadTasks(false);
    }

    public void loadTasks(boolean forceUpdate) {
        loadTasks(forceUpdate, true);
    }

    public void setFiltering(Filter filter) {
        this.filter = filter;

        switch (filter) {
            case ALL:
                break;
            case ACTIVE:
                break;
            case COMPLETED:
                break;
        }
    }

    private void loadTasks(boolean forceUpdate, boolean loadingUi) {

    }
}
