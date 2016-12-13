package com.victorldavila.funnyguide.view.presenters.interactors;

import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.database.GenreDAO;
import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.ResponseGenre;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.view.OnViewListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.Subscription;

/**
 * Created by victo on 12/12/2016.
 */

public class MovieInteractor {

    private OnViewListener<Movie> view;
    private FunnyApi api;
    private Realm realm;

    public MovieInteractor(OnViewListener<Movie> view, FunnyApi api) {
        this.view = view;
        this.api = api;
    }

    public void bind(){
        realm = Realm.getDefaultInstance();
    }

    public void unbind(){
        realm.close();
    }

    public Subscription getMoviesGenre(int genreId){
        Observable<ResponseListItem<Movie>> genreResponseObservable = (Observable<ResponseListItem<Movie>>)api.getPreparedObservable(api.getAPI()
                .getMoviesGenreObservable(genreId, api.getQueryString())
                , Genre.class
                , true
                , true);

        return genreResponseObservable.subscribe(responseTmdb ->
                view.onItemList(responseTmdb.getResults()));
    }
}
