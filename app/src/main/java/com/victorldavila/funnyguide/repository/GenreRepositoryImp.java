package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.api.ErrorHandler;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.ResponseGenre;
import com.victorldavila.funnyguide.view.presenters.RxResponse;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victo on 26/03/2017.
 */

public class GenreRepositoryImp implements GenreRepository {

    private FunnyApi funnyApi;

    public GenreRepositoryImp(FunnyApi funnyApi) {
        this.funnyApi = funnyApi;
    }

    @Override
    public Subscription getMovieGenre(RxResponse<ResponseGenre> rxResponse) {
        return getMovieGenreObservable().subscribe(responseGenre -> rxResponse.onNext(responseGenre)
            , throwable -> rxResponse.onError(ErrorHandler.parseError(throwable))
            , () -> rxResponse.onComplete());
    }

    private Observable<ResponseGenre> getMovieGenreObservable(){
        return funnyApi.getAPI().getGenreObservable(funnyApi.getQueryString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
