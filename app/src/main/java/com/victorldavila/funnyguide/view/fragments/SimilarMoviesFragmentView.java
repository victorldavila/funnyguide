package com.victorldavila.funnyguide.view.fragments;


import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.models.ResponseMovie;
import com.victorldavila.funnyguide.view.ResponseView;

public interface SimilarMoviesFragmentView extends ResponseView<ResponseMovie> {
  void changeActivity(ResponseMovie responseMovie, SimpleDraweeView image);
  void setLoadRecycler(boolean isLoad);

  int getMovieId();
}
