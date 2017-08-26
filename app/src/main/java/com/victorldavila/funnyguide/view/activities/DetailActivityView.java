package com.victorldavila.funnyguide.view.activities;

import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.Tv;

public interface DetailActivityView {
  void setImageUrlPoster(String urlPoster);
  void setOverViewInfo(String overview);
  void setTitleInfo(String title);
  void setOriginalTitleInfo(String originalTitle);
  void setRateInfo(String rate);
  void setDateInfo(String date);
  void setLanguageInfo(String language);
  void setGenreInfo(String genre);

  Movie getMovie();
  Tv getTv();
}
