package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.models.ResponseMovie;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.models.ResponseReview;

import rx.Observable;

public interface MovieRepository {
  Observable<ResponseMovie> getMovie(int movieId);
  Observable<ResponseListItem<ResponseMovie>> getMovieListGenre(int genreId, int page);
  Observable<ResponseListItem<ResponseReview>> getMovieReviews(int movieId, int page);
  Observable<ResponseListItem<ResponseMovie>> getSimilarMovies(int movieId, int page);
}
