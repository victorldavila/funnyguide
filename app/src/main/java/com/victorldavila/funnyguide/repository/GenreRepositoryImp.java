package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.ResponseListGenre;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GenreRepositoryImp implements GenreRepository {
  private FunnyApi funnyApi;

  public GenreRepositoryImp(FunnyApi funnyApi) {
        this.funnyApi = funnyApi;
    }

  @Override
  public Observable<ResponseListGenre> getMovieGenre(){
    return funnyApi.getAPI().getGenreObservable(funnyApi.getQueryString())
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  }
}
