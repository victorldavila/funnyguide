package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.models.ResponseGenre;

import rx.Observable;
import rx.Subscription;

/**
 * Created by victo on 26/03/2017.
 */

public interface GenreRepository {
    Observable<ResponseGenre> getMovieGenre();
}
