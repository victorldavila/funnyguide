package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.models.Tv;
import com.victorldavila.funnyguide.view.presenters.RxResponse;

import rx.Observable;
import rx.Subscription;

/**
 * Created by victor on 26/03/2017.
 */

public interface TvRepository {

    Subscription getTvTopRated(int page, RxResponse<ResponseListItem<Tv>> rxResponse);
    Observable<ResponseListItem<Tv>> getTvTopRatedObservable(int page);
}
