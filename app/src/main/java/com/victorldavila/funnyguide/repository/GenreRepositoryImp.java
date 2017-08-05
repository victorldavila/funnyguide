package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.api.ErrorHandler;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.ResponseGenre;

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
    public Observable<ResponseGenre> getMovieGenre(){
        return funnyApi.getAPI().getGenreObservable(funnyApi.getQueryString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
