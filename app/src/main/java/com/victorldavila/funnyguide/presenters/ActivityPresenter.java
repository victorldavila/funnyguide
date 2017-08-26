package com.victorldavila.funnyguide.presenters;

public interface ActivityPresenter<T> extends BasePresenter<T>{
  void onCreate();
  void onDestroy();
}
