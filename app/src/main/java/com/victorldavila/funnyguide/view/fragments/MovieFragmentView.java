package com.victorldavila.funnyguide.view.fragments;

import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.view.OnViewListener;

/**
 * Created by victo on 24/03/2017.
 */

public interface MovieFragmentView extends OnViewListener<Movie> {
    void changeActivity(Movie movie, SimpleDraweeView image);
}
