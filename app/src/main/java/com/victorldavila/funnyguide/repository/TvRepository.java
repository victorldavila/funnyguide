package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.models.Tv;

import rx.Observable;
import rx.Subscription;

/**
 * Created by victor on 26/03/2017.
 */

public interface TvRepository {
    Observable<ResponseListItem<Tv>> getTvTopRated(int page);
    Observable<Tv> getTv(int tvId);
}
