package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.models.ResponseGenre;
import com.victorldavila.funnyguide.presenters.RxResponse;

import rx.Subscription;

/**
 * Created by victo on 26/03/2017.
 */

public interface GenreRepository {
    Subscription getMovieGenre(RxResponse<ResponseGenre> rxResponse);
}
