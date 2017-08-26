package com.victorldavila.funnyguide.presenters;

public interface FragmentPresenter<T> extends BasePresenter<T>{
  void onViewCreated();
  void onDestroyView();
}
