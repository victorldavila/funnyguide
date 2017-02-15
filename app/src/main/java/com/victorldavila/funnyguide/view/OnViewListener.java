package com.victorldavila.funnyguide.view;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by victor on 10/12/2016.
 */

public interface OnViewListener<T> {
    void onItemList(List<T> results);
    void onError(String error);
    void onComplete();
}
