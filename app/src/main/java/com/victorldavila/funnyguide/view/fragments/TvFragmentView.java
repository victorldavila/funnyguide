package com.victorldavila.funnyguide.view.fragments;

import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.models.Tv;
import com.victorldavila.funnyguide.view.OnViewListener;

/**
 * Created by victo on 26/03/2017.
 */

public interface TvFragmentView extends OnViewListener<Tv> {
    void changeActivity(Tv tv, SimpleDraweeView image);
    void setLoadRecycler(boolean isLoad);
}
