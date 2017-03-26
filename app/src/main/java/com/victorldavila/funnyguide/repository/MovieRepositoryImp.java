package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.api.ErrorHandler;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.view.presenters.RxResponse;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 26/03/2017.
 */

public class MovieRepositoryImp implements MovieRepository{

    private FunnyApi funnyApi;

    public MovieRepositoryImp(FunnyApi funnyApi) {
        this.funnyApi = funnyApi;
    }

    @Override
    public Subscription getMovieGenre(int genreId, int page, RxResponse<ResponseListItem<Movie>> presenterView) {
        return getMovieListGenre(genreId, page).subscribe(movieResponseListItem -> presenterView.onNext(movieResponseListItem)
                , throwable -> presenterView.onError(ErrorHandler.parseError(throwable))
                , () -> presenterView.onComplete());
    }

    @Override
    public Observable<ResponseListItem<Movie>> getMovieListGenre(int genreId, int page){
        return funnyApi.getAPI().getMoviesGenreObservable(genreId, funnyApi.getQueryStringList(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}