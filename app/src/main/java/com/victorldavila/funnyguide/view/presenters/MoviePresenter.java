package com.victorldavila.funnyguide.view.presenters;

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

public class MoviePresenter implements OnPresenterListener {

    private OnViewListener<Movie> view;
    private MovieInteractor interactor;

    private ArrayList<Subscription> subscriptions;
    private Subscription genreSubscription;

    public MoviePresenter(OnViewListener<Movie> view, FunnyApi api){
        this.view = view;

        subscriptions = new ArrayList<Subscription>();
        interactor = new MovieInteractor(view, api);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }
}
