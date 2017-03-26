package com.victorldavila.funnyguide.presenters;

/**
 * Created by victo on 05/03/2017.
 */

public interface ActivityPresenter<T> extends BasePresenter<T>{
    void onCreate();
    void onDestroy();
}
