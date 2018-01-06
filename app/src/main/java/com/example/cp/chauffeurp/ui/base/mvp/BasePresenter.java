package com.example.cp.chauffeurp.ui.base.mvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by fanilogabaud on 06/01/2018.
 */

public abstract class BasePresenter<V extends BaseView> {

    public V view;
    //Do not forget to cache observable
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    /**
     * attach a view to this presenter.
     *
     * @param view - The View (The 'V' of "MVP")
     */
    public void attachView(V view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
    }

    /**
     * Setup the presenter.
     * <p>
     * Called in the onCreate()/onActivityCreated() methods
     */
    public abstract void initialize();

    /**
     * Perform any presenter cleanup here.
     * i.e. un-subscribing from any subscriptions
     * <p>
     * Called in onDestroyPresenter()/onDestroyView()
     */
    public void onDestroyPresenter() {
        compositeSubscription.unsubscribe();
    }

    public Subscription bindSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
        return subscription;
    }
}
