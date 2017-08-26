package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.ResponseListItem;

import rx.Observable;

public interface MovieRepository {
  Observable<Movie> getMovie(int movieId);
  Observable<ResponseListItem<Movie>> getMovieListGenre(int genreId, int page);
}
