package com.victorldavila.funnyguide.view.fragments;

import com.victorldavila.funnyguide.models.ResponseReview;
import com.victorldavila.funnyguide.view.ResponseView;

public interface ReviewFragmentView extends ResponseView<ResponseReview> {
  void setLoadRecycler(boolean isLoad);

  int getMovieId();
}
