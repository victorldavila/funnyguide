package com.victorldavila.funnyguide.presenters;

/**
 * Created by victo on 05/03/2017.
 */

public interface FragmentPresenter<T> extends BasePresenter<T>{
    void onViewCreated();
    void onDestroyView();
}
