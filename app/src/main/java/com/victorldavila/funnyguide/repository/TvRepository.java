package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.models.ResponseTv;

import rx.Observable;

public interface TvRepository {
  Observable<ResponseListItem<ResponseTv>> getTvTopRated(int page);
  Observable<ResponseTv> getTv(int tvId);
}
