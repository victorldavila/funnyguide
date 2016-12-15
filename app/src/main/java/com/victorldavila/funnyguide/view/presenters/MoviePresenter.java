package com.victorldavila.funnyguide.view.presenters;

import android.text.TextUtils;

import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.view.OnViewListener;
import com.victorldavila.funnyguide.view.presenters.interactors.GenreInteractor;
import com.victorldavila.funnyguide.view.presenters.interactors.MovieInteractor;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by victo on 12/12/2016.
 */

public class MoviePresenter implements OnFragmentPresenterListener {

    private OnViewListener<Movie> view;
    private MovieInteractor interactor;

    private ArrayList<Subscription> subscriptions;
    private Subscription movieSubscribe;

    private int genreId;
    private int page;

    public MoviePresenter(OnViewListener<Movie> view, FunnyApi api){
        this.view = view;
        this.page = 0;

        subscriptions = new ArrayList<Subscription>();
        interactor = new MovieInteractor(view, this, api);
    }

    @Override
    public void onStop() {
        rxUnSubscribe();
    }

    @Override
    public void onStart() {
        this.page = 0;
        getMoviesGenre();
    }

    public boolean isLoad() {
        return interactor.isLoad();
    }

    public String getText(String text) {
        if(!TextUtils.isEmpty(text))
            return text;
        else
            return "";
    }

    public void getMoviesGenre(){
        rxUnSubscribe(movieSubscribe);
        if(interactor != null)
            movieSubscribe = interactor.getMoviesGenre(genreId, page);
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

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    @Override
    public void bind() {
        if(interactor != null)
            interactor.bind();
    }

    @Override
    public void unbind() {
        if(interactor != null)
            interactor.unbind();
    }

    public void countPage() {
        page++;
    }
}
