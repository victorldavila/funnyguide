package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.api.ErrorHandler;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.models.Tv;
import com.victorldavila.funnyguide.presenters.RxResponse;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victo on 26/03/2017.
 */

public class TvRepositoryImp implements TvRepository{

    private FunnyApi funnyApi;

    public TvRepositoryImp(FunnyApi funnyApi) {
        this.funnyApi = funnyApi;
    }

    @Override
    public Subscription getTvTopRated(int page, RxResponse<ResponseListItem<Tv>> rxResponse) {
        return getTvTopRatedObservable(page).subscribe(tvResponseListItem -> rxResponse.onNext(tvResponseListItem)
            , throwable -> rxResponse.onError(ErrorHandler.parseError(throwable))
            , () -> rxResponse.onComplete());
    }

    @Override
    public Subscription getTv(int tvId, RxResponse<Tv> rxResponse) {
        return getTvObservable(tvId).subscribe(tv -> rxResponse.onNext(tv)
                ,throwable -> rxResponse.onError(ErrorHandler.parseError(throwable))
                , () -> rxResponse.onComplete());
    }

    @Override
    public Observable<Tv> getTvObservable(int tvId) {
        return funnyApi.getAPI().getSerieObservable(tvId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ResponseListItem<Tv>> getTvTopRatedObservable(int page) {
        return funnyApi.getAPI().getSeriesTopRateObservable(funnyApi.getQueryStringList(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
