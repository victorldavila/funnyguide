package com.victorldavila.funnyguide.view.fragments;

import com.victorldavila.funnyguide.models.ResponseReview;
import com.victorldavila.funnyguide.view.ResponseView;

public interface ReviewFragmentView extends ResponseView<ResponseReview> {
  int getMovieId();

  void setLoadRecycler(boolean isLoad);
  void enableEmptyState();
  void finishLoadReview();
}
