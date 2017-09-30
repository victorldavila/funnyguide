package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.models.ResponseMovie;
import com.victorldavila.funnyguide.models.ResponseListItem;

import rx.Observable;

public interface MovieRepository {
  Observable<ResponseMovie> getMovie(int movieId);
  Observable<ResponseListItem<ResponseMovie>> getMovieListGenre(int genreId, int page);
}
