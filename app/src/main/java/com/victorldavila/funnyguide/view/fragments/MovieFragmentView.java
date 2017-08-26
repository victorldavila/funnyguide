package com.victorldavila.funnyguide.view.fragments;

import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.view.ResponseView;

public interface MovieFragmentView extends ResponseView<Movie> {
  void changeActivity(Movie movie, SimpleDraweeView image);
  void setLoadRecycler(boolean isLoad);

  int getGenreId();
}
