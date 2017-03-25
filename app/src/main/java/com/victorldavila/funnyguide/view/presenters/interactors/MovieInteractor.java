package com.victorldavila.funnyguide.view.presenters.interactors;

import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.ResponseListItem;

import rx.Observable;
import rx.Subscription;

/**
 * Created by victor on 12/12/2016.
 */

public class MovieInteractor {

    private FunnyApi api;

    public MovieInteractor(FunnyApi api) {
        this.api = api;
    }

    public Subscription getMoviesGenre(int genreId, final int page, RxResponse<ResponseListItem<Movie>> presenterView){
        return getMovieListObservable(genreId, page).subscribe(movieResponseListItem -> view.onItemList(movieResponseListItem.getResults())
                , e -> view.onError(e.getMessage())
                , () -> view.onComplete());
    }

    private Observable<ResponseListItem<Movie>> getMovieListObservable(int genreId, int page){
        return (Observable<ResponseListItem<Movie>>)api.getPreparedObservable(api.getAPI()
                        .getMoviesGenreObservable(genreId, api.getQueryStringList(page))
                , Movie.class
                , true
                , true);
    }
}
