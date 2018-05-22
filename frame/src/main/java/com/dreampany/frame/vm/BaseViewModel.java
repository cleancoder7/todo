package com.dreampany.frame.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dreampany.frame.rx.RxFacade;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Hawladar Roman on 5/21/2018.
 * Dreampany Ltd
 * dreampanymail@gmail.com
 */
public class BaseViewModel<T> extends AndroidViewModel {

    @NonNull
    protected final RxFacade facade;
    @NonNull
    protected final CompositeDisposable disposables;

    @NonNull
    protected final MutableLiveData<String> liveTitle;
    @NonNull
    protected final MutableLiveData<String> liveSubtitle;
    @Nullable
    protected T t;

    protected BaseViewModel(@NonNull Application application, @NonNull RxFacade facade) {
        super(application);
        this.facade = facade;
        disposables = new CompositeDisposable();
        liveTitle = new MutableLiveData<>();
        liveSubtitle = new MutableLiveData<>();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    protected void addSubscription(Disposable... disposables) {
        this.disposables.addAll(disposables);
    }

    @NonNull
    public MutableLiveData<String> getLiveTitle() {
        return liveTitle;
    }

    @NonNull
    public MutableLiveData<String> getLiveSubtitle() {
        return liveSubtitle;
    }

    public void setT(@Nullable T t) {
        this.t = t;
    }

    @Nullable
    public T getT() {
        return t;
    }
}
