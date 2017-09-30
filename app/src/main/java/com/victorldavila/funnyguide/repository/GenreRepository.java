package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.models.ResponseListGenre;

import rx.Observable;

public interface GenreRepository {
  Observable<ResponseListGenre> getMovieGenre();
}
