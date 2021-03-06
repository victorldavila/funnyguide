package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.ResponseMovie;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.models.ResponseReview;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieRepositoryImp implements MovieRepository{
  private FunnyApi funnyApi;

  public MovieRepositoryImp(FunnyApi funnyApi) {
        this.funnyApi = funnyApi;
    }

  @Override
  public Observable<ResponseMovie> getMovie(int movieId) {
    return funnyApi.getAPI().getMovieObservable(movieId)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  }

  @Override
  public Observable<ResponseListItem<ResponseMovie>> getMovieListGenre(int genreId, int page) {
    return funnyApi.getAPI().getMoviesGenreObservable(genreId, funnyApi.getQueryStringListPt(page))
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  }

  @Override
  public Observable<ResponseListItem<ResponseReview>> getMovieReviews(int movieId, int page) {
    return funnyApi.getAPI().getMovieReviewsObservable(movieId, funnyApi.getQueryStringList(page))
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  }

  @Override
  public Observable<ResponseListItem<ResponseMovie>> getSimilarMovies(int movieId, int page) {
    return funnyApi.getAPI().getSimilarMovieObservable(movieId, funnyApi.getQueryStringListPt(page))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
