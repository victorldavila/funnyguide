package com.victorldavila.funnyguide.view.activities;

import com.victorldavila.funnyguide.models.ResponseMovie;
import com.victorldavila.funnyguide.models.ResponseTv;

public interface DetailActivityView {
  void setImageUrlPoster(String urlPoster);

  ResponseMovie getResponseMovie();
  ResponseTv getResponseTv();
}
