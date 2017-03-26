package com.victorldavila.funnyguide.presenters;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by victor on 26/03/2017.
 */

public class BaseRxPresenter {

    private CompositeSubscription compositeSubscription;

    public BaseRxPresenter() {
        compositeSubscription = new CompositeSubscription();
    }

    protected void rxUnSubscribe(Subscription subscription){
        if(subscription != null && !subscription.isUnsubscribed()) {
            compositeSubscription.remove(subscription);
            subscription.unsubscribe();
        }
    }

    public void rxUnSubscribe(){
        compositeSubscription.unsubscribe();
    }

    protected void addSubscription(Subscription subscription){
        compositeSubscription.add(subscription);
    }
}
