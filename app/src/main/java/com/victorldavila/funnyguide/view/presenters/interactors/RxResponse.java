package com.victorldavila.funnyguide.view.presenters.interactors;

import com.victorldavila.funnyguide.models.NetWorkError;

/**
 * Created by victo on 25/03/2017.
 */

public interface RxResponse<T> {
    void onNext(T result);
    void onError(NetWorkError error);
    void onComplete();
}
