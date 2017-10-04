package com.victorldavila.funnyguide.api;

import com.victorldavila.funnyguide.models.ResponseMovie;
import com.victorldavila.funnyguide.models.ResponseListGenre;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.models.ResponseReview;
import com.victorldavila.funnyguide.models.ResponseTv;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface TmdbAPI {
  @GET(FunnyApi.EndPoint.GENRE + "/" + FunnyApi.EndPoint.MOVIE + "/" + FunnyApi.EndPoint.LIST)
  Observable<ResponseListGenre> getGenreObservable(@QueryMap Map<String, String> options);

  @GET(FunnyApi.EndPoint.GENRE + "/{genre_id}/" + FunnyApi.EndPoint.MOVIES)
  Observable<ResponseListItem<ResponseMovie>> getMoviesGenreObservable(@Path("genre_id") int genreId, @QueryMap Map<String, String> options);

  @GET(FunnyApi.EndPoint.MOVIE + "/{movie_id}")
  Observable<ResponseMovie> getMovieObservable(@Path("movie_id") int movieId);

  @GET(FunnyApi.EndPoint.MOVIE + "/{movie_id}/" + FunnyApi.EndPoint.REVIEWS)
  Observable<ResponseListItem<ResponseReview>> getMovieReviewsObservable(@Path("movie_id") int movieId, @QueryMap Map<String, String> options);

  @GET(FunnyApi.EndPoint.TV + "/" + FunnyApi.EndPoint.TOP_RATED)
  Observable<ResponseListItem<ResponseTv>> getSeriesTopRateObservable(@QueryMap Map<String, String> options);

  @GET(FunnyApi.EndPoint.TV + "/{tv_id}")
  Observable<ResponseTv> getSerieObservable(@Path("tv_id") int tvId);
}
