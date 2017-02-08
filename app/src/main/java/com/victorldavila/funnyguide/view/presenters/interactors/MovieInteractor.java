package com.victorldavila.funnyguide.view.presenters.interactors;

import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.database.GenreDAO;
import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.ResponseGenre;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.models.Tv;
import com.victorldavila.funnyguide.view.OnViewListener;
import com.victorldavila.funnyguide.view.presenters.MoviePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by victo on 12/12/2016.
 */

public class MovieInteractor {

    private OnViewListener<Movie> view;
    private final MoviePresenter presenter;
    private FunnyApi api;
    //private Realm realm;

    private boolean isLoad;

    public MovieInteractor(OnViewListener<Movie> view, MoviePresenter presenter, FunnyApi api) {
        this.view = view;
        this.presenter = presenter;
        this.api = api;

        isLoad = true;
    }

    public void bind(){
        //realm = Realm.getDefaultInstance();
    }

    public void unbind(){
        //realm.close();
    }

    public Subscription getMoviesGenre(int genreId, final int page){
        Observable<ResponseListItem<Movie>> genreResponseObservable = (Observable<ResponseListItem<Movie>>)api.getPreparedObservable(api.getAPI()
                .getMoviesGenreObservable(genreId, api.getQueryStringList(page))
                , Movie.class
                , true
                , true);

        return genreResponseObservable.subscribe(movieResponseListItem -> {
            if(movieResponseListItem.getResults() != null) {
                if (movieResponseListItem.getResults().size() == 0 || page == movieResponseListItem.getTotal_pages())
                    isLoad = false;
                else
                    presenter.countPage();

                view.onItemList(movieResponseListItem.getResults());
            } else{
                view.onError("Results null");
            }
        }, e -> {
            isLoad = false;
            view.onError(e.getMessage());
        });
    }

    public boolean isLoad() {
        return isLoad;
    }
}
