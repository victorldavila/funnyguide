package com.victorldavila.funnyguide.view.presenters;

import com.victorldavila.funnyguide.view.OnViewListener;

/**
 * Created by victo on 14/12/2016.
 */

public interface OnFragmentPresenterListener extends OnPresenterListener {
    void bind();
    void unbind();
}
