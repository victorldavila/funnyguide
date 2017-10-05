package com.victorldavila.funnyguide.view.fragments;


import com.victorldavila.funnyguide.models.Season;
import com.victorldavila.funnyguide.view.ResponseView;

import java.util.List;

public interface TvSeasonFragmentView extends ResponseView<Season> {
  void setLoadRecycler(boolean isLoad);

  int getTvId();
}
