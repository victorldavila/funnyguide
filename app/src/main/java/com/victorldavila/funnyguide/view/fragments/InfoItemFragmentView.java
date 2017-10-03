package com.victorldavila.funnyguide.view.fragments;


import com.victorldavila.funnyguide.models.ResponseMovie;
import com.victorldavila.funnyguide.models.ResponseTv;

public interface InfoItemFragmentView {
  void setOverViewInfo(String overview);
  void setTitleInfo(String title);
  void setOriginalTitleInfo(String originalTitle);
  void setRateInfo(String rate);
  void setDateInfo(String date);
  void setLanguageInfo(String language);
  void setGenreInfo(String genre);
  void setStatus(String status);
  void setProductionCountries(String countries);
  void setProductionCompanies(String companies);
}
