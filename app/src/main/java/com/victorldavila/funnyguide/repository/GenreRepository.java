package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.models.ResponseGenre;

import rx.Observable;

public interface GenreRepository {
  Observable<ResponseGenre> getMovieGenre();
}
