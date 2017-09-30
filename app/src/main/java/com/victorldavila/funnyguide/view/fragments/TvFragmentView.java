package com.victorldavila.funnyguide.view.fragments;

import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.models.ResponseTv;
import com.victorldavila.funnyguide.view.ResponseView;

public interface TvFragmentView extends ResponseView<ResponseTv> {
  void changeActivity(ResponseTv responseTv, SimpleDraweeView image);
  void setLoadRecycler(boolean isLoad);
}
