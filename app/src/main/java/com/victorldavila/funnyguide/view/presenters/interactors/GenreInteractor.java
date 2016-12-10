package com.victorldavila.funnyguide.view.presenters.interactors;

import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.database.GenreDAO;
import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.view.OnViewListener;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victo on 10/12/2016.
 */

public class GenreInteractor {
    private OnViewListener<Genre> view;
    private FunnyApi api;
    private GenreDAO genreDAO;
    private Realm realm;

    public GenreInteractor(OnViewListener<Genre> view, FunnyApi api) {
        this.view = view;
        this.api = api;

        genreDAO = new GenreDAO();
    }

    public void bind(){
        realm = Realm.getDefaultInstance();
    }

    public void unbind(){
        realm.close();
    }

    public Subscription getGenreMovie(){
        Observable<ArrayList<Genre>> genreResponseObservable = (Observable<ArrayList<Genre>>)api.getPreparedObservable(api.getAPI().getGenreObservable(api.getQueryString()), Genre.class, true, true);

        return genreResponseObservable.subscribe(new Observer<ArrayList<Genre>>(){
            @Override
            public void onCompleted(){}

            @Override
            public void onError(Throwable e){
                //handle error
            }

            @Override
            public void onNext(ArrayList<Genre> response){
                //handle response
                genreDAO.storeItemList(response);
            }
        });
    }

    public void listenGenreDatabase(){
        realm.where(Genre.class)
                .findAllAsync()
                .asObservable()
                .filter(RealmResults::isLoaded)
                .filter(RealmResults::isValid)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(genres -> {
                    view.onChangeItem(genres);
                });
    }

    public Subscription getGenresFromRetrofit() {
        return api.getAPI().getGenreObservable(api.getQueryString())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(genres -> {
                    return realm.executeTransactionAsync(realm1 -> realm1.copyToRealmOrUpdate(genres));
                }).subscribe();
    }
}
