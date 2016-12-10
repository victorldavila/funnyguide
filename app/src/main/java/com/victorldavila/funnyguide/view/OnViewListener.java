package com.victorldavila.funnyguide.view;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by victo on 10/12/2016.
 */

public interface OnViewListener<T> {
    void onItemList(ArrayList<T> results);
    void onChangeItem(RealmResults result);
    void onError(String error);
}
