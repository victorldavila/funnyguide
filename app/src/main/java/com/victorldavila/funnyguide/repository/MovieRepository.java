package com.victorldavila.funnyguide.repository;

import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.view.presenters.RxResponse;

import rx.Observable;
import rx.Subscription;

/**
 * Created by victo on 26/03/2017.
 */

public interface MovieRepository {
    Subscription getMovieGenre(int genreId, final int page, RxResponse<ResponseListItem<Movie>> presenterView);
    Observable<ResponseListItem<Movie>> getMovieListGenre(int genreId, int page);
}
