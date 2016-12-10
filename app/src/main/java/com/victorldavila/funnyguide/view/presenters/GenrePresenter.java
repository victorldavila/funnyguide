package com.victorldavila.funnyguide.view.presenters;

import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.view.OnViewListener;
import com.victorldavila.funnyguide.view.presenters.interactors.GenreInteractor;

import java.util.ArrayList;

import rx.Subscription;

/**
 * Created by victor on 10/12/2016.
 */

public class GenrePresenter implements OnPresenterListener {

    private OnViewListener<Genre> view;
    private GenreInteractor interactor;

    private ArrayList<Subscription> subscriptions;
    private Subscription genreSubscription;

    public GenrePresenter(OnViewListener<Genre> view, FunnyApi api){
        this.view = view;
        interactor = new GenreInteractor(view, api);
    }

    @Override
    public void onCreate() {
        interactor.bind();
        interactor.listenGenreDatabase();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {
        getGenre();
    }

    @Override
    public void onDestroy() {
        rxUnSubscribe();
        interactor.unbind();
    }

    public void getGenre(){
        rxUnSubscribe(genreSubscription);
        genreSubscription = interactor.getGenresFromRetrofit();
        subscriptions.add(genreSubscription);
    }

    private void rxUnSubscribe(Subscription subscription){
        if(subscription!=null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    public void rxUnSubscribe(){
        for (Subscription subscription : subscriptions) {
            if(subscription!=null && !subscription.isUnsubscribed())
                subscription.unsubscribe();
        }
    }
}
