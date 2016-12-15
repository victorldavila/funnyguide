package com.victorldavila.funnyguide.view.presenters.interactors;

import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.database.GenreDAO;
import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.ResponseGenre;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.view.OnViewListener;
import com.victorldavila.funnyguide.view.presenters.MoviePresenter;

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
    private final MoviePresenter presenter;
    private FunnyApi api;
    private Realm realm;

    private boolean isLoad;

    public MovieInteractor(OnViewListener<Movie> view, MoviePresenter presenter, FunnyApi api) {
        this.view = view;
        this.presenter = presenter;
        this.api = api;

        isLoad = true;
    }

    public void bind(){
        realm = Realm.getDefaultInstance();
    }

    public void unbind(){
        realm.close();
    }

    public Subscription getMoviesGenre(int genreId, int page){
        Observable<ResponseListItem<Movie>> genreResponseObservable = (Observable<ResponseListItem<Movie>>)api.getPreparedObservable(api.getAPI()
                .getMoviesGenreObservable(genreId, api.getQueryString())
                , Genre.class
                , true
                , true);

        return genreResponseObservable.subscribe(responseTmdb ->{
                if(responseTmdb.getResults().size() == 0 || page == responseTmdb.getTotal_pages())
                    isLoad = false;
                else
                    presenter.countPage();

                view.onItemList(responseTmdb.getResults());
        }, throwable -> {
            isLoad = false;

            view.onError(throwable.getMessage());
        });
    }

    public boolean isLoad() {
        return isLoad;
    }
}
