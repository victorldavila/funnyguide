package com.victorldavila.funnyguide.view;

import java.util.List;

import io.realm.RealmResults;

public interface ResponseView<T> {
  void onItemList(List<T> results);
  void onError(String error);
}
