package com.compact.executor;

import io.reactivex.CompletableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.SingleTransformer;

public class RxCompactSchedulers {

    private final Scheduler subscribeOn;
    private final Scheduler observeOn;

    public RxCompactSchedulers(Scheduler subscribeOn, Scheduler observeOn) {
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    public RxCompactSchedulers(WorkerThread subscribeOn, MainThread observeOn) {
        this(subscribeOn.getScheduler(), observeOn.getScheduler());
    }

    public <T> ObservableTransformer<T, T> applyOnObservable() {
        return upstream -> upstream.subscribeOn(subscribeOn).observeOn(observeOn).unsubscribeOn(subscribeOn);
    }

    public <T> SingleTransformer<T, T> applyOnSingle() {
        return upstream -> upstream.subscribeOn(subscribeOn).observeOn(observeOn).unsubscribeOn(subscribeOn);
    }

    public CompletableTransformer applyOnCompletable() {
        return upstream -> upstream.subscribeOn(subscribeOn).observeOn(observeOn).unsubscribeOn(subscribeOn);
    }

    public Scheduler workerExecutor() {
        return subscribeOn;
    }

    public Scheduler mainExecutor() {
        return observeOn;
    }
}
