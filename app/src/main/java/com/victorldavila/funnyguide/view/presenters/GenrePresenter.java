package com.victorldavila.funnyguide.view.presenters;

import android.support.v4.app.Fragment;

import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.view.OnViewListener;
import com.victorldavila.funnyguide.view.fragments.MovieFragment;
import com.victorldavila.funnyguide.view.presenters.interactors.GenreInteractor;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by victor on 10/12/2016.
 */

public class GenrePresenter implements OnFragmentPresenterListener {

    private OnViewListener<Genre> view;
    private GenreInteractor interactor;

    private ArrayList<Subscription> subscriptions;
    private Subscription genreSubscription;

    public GenrePresenter(OnViewListener<Genre> view, FunnyApi api){
        this.view = view;

        subscriptions = new ArrayList<Subscription>();
        interactor = new GenreInteractor(view, api);
    }

    @Override
    public void onStart() {
        getGenre();
    }

    @Override
    public void onStop() {
        rxUnSubscribe();
    }

    public void getGenre(){
        rxUnSubscribe(genreSubscription);
        if(interactor != null) {
            genreSubscription = interactor.getGenreMovie();
            subscriptions.add(genreSubscription);
        }
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

    public Fragment getItemGenre(List<Genre> genres, int position){
        if(genres != null)
            return MovieFragment.newInstance(genres.get(position).getId());
        else
            return MovieFragment.newInstance();
    }

    @Override
    public void bind() {
        if(interactor != null) {
            interactor.bind();
        }
    }

    @Override
    public void unbind() {
        if(interactor != null)
            interactor.unbind();
    }
}
