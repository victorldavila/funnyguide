package com.victorldavila.funnyguide.view.presenters;

import android.support.v4.app.Fragment;

import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.view.OnViewListener;
import com.victorldavila.funnyguide.view.fragments.MovieFragment;
import com.victorldavila.funnyguide.view.presenters.interactors.GenreInteractor;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by victor on 10/12/2016.
 */

public class GenrePresenter implements FragmentPresenter {

    private OnViewListener<Genre> view;
    private GenreInteractor interactor;

    private CompositeSubscription compositeSubscription;
    private Subscription genreSubscription;

    public GenrePresenter(OnViewListener<Genre> view, FunnyApi api){
        this.view = view;

        compositeSubscription = new CompositeSubscription();
        interactor = new GenreInteractor(view, api);
    }

    @Override
    public void onAttach() {
        if(interactor != null)
            interactor.bind();
    }

    @Override
    public void onDettach() {
        if(interactor != null)
            interactor.unbind();
    }

    @Override
    public void onViewCreated() {
        getGenre();
    }

    @Override
    public void onDestroyView() {
        rxUnSubscribe();
    }

    public void getGenre(){
        rxUnSubscribe(genreSubscription);
        if(interactor != null) {
            genreSubscription = interactor.getGenreMovie();
            compositeSubscription.add(genreSubscription);
        }
    }

    private void rxUnSubscribe(Subscription subscription){
        if(subscription!=null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    public void rxUnSubscribe(){
        compositeSubscription.unsubscribe();
    }

    public Fragment getItemGenre(List<Genre> genres, int position){
        if(genres != null)
            return MovieFragment.newInstance(genres.get(position).getId());
        else
            return MovieFragment.newInstance();
    }
}
