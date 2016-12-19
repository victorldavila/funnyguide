package com.victorldavila.funnyguide.view.presenters.interactors;

import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.database.GenreDAO;
import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.models.ResponseGenre;
import com.victorldavila.funnyguide.view.OnViewListener;

import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

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
        //realm = Realm.getDefaultInstance();
    }

    public void unbind(){
        //realm.close();
    }

    public Subscription getGenreMovie(){
        Observable<ResponseGenre> genreResponseObservable = (Observable<ResponseGenre>)api.getPreparedObservable(api.getAPI().getGenreObservable(api.getQueryString()), Genre.class, true, true);

        return genreResponseObservable.subscribe(new Observer<ResponseGenre>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.onError(e.getMessage());
            }

            @Override
            public void onNext(ResponseGenre responseGenre) {
                view.onItemList(responseGenre.getGenres());
            }
        });
    }

    /*public Subscription listenGenreDatabase(){
        return realm.where(Genre.class)
                .findAllAsync()
                .asObservable()
                .filter(RealmResults::isLoaded)
                .filter(RealmResults::isValid)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(genres -> {
                    view.onChangeItem(genres);
                });
    }*/

    /*public Subscription getGenresFromRetrofit() {
        return api.getAPI().getGenreObservable(api.getQueryString())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(genres -> {
                    return realm.executeTransactionAsync(realm1 -> realm1.copyToRealmOrUpdate(genres.getGenres()));
                }).subscribe();
    }*/

    /*public Observable<RealmResults<Genre>> getGenres() {
        // Realm will automatically notify this observable whenever data is saved from the network
        return realm.where(Genre.class).findAllAsync().asObservable()
                .filter(RealmResults::isLoaded)
                .doOnNext(results -> {
                    if (results.size() == 0) {
                        loadGenreFromNetwork();
                    }
                });
    }

    private void loadGenreFromNetwork() {
        api.getAPI().getGenreObservable(api.getQueryString())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    try(Realm realm = Realm.getDefaultInstance()) {
                        realm.executeTransaction(r -> r.insertOrUpdate(response.getGenres()));
                    } // auto-close
                });
    }*/
}
