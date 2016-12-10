package com.victorldavila.funnyguide.database;

import com.victorldavila.funnyguide.models.Genre;

import java.util.List;

import io.realm.Realm;

/**
 * Created by victor on 10/12/2016.
 */

public class GenreDAO implements OnDAOListener<Genre> {

    @Override
    public List<Genre> getItemList() {
        Realm realm = Realm.getDefaultInstance();
        List<Genre> genreList = realm.copyFromRealm(realm.where(Genre.class).findAll());
        realm.close();
        return genreList;
    }

    @Override
    public void saveItem(Genre item) {

    }

    @Override
    public void storeItem(Genre item) {

    }

    @Override
    public void storeItemList(final List<Genre> items) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(items);
            }
        });
        realm.close();
    }

    @Override
    public void deleteItem(Genre item) {

    }

    @Override
    public void clearDatabase() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Genre.class);
            }
        });
        realm.close();
    }
}
