package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.models.ResponseTv;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TvRepositoryImp implements TvRepository{
  private FunnyApi funnyApi;

  public TvRepositoryImp(FunnyApi funnyApi) {
        this.funnyApi = funnyApi;
    }

  @Override
  public Observable<ResponseListItem<ResponseTv>> getTvTopRated(int page) {
    return funnyApi.getAPI().getSeriesTopRateObservable(funnyApi.getQueryStringList(page))
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  }

  @Override
  public Observable<ResponseTv> getTv(int tvId) {
    return funnyApi.getAPI().getSerieObservable(tvId)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  }
}
