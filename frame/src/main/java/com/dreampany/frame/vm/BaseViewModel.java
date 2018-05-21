package com.dreampany.frame.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

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
public class BaseViewModel extends AndroidViewModel {

    @NonNull
    protected final RxFacade facade;
    @NonNull
    protected final CompositeDisposable disposables;

    @NonNull
    protected final BehaviorSubject<String> title;
    @NonNull
    protected final BehaviorSubject<String> subtitle;

    protected BaseViewModel(@NonNull Application application, @NonNull RxFacade facade) {
        super(application);
        this.facade = facade;
        disposables = new CompositeDisposable();
        title = BehaviorSubject.create();
        subtitle = BehaviorSubject.create();
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
    public Observable<String> getTitle() {
        return title.hide();
    }

    @NonNull
    public Observable<String> getSubtitle() {
        return subtitle.hide();
    }
}
