package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.models.Tv;

import rx.Observable;

public interface TvRepository {
  Observable<ResponseListItem<Tv>> getTvTopRated(int page);
  Observable<Tv> getTv(int tvId);
}
