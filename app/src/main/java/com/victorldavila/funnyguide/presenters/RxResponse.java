package com.victorldavila.funnyguide.presenters;

import com.victorldavila.funnyguide.models.NetWorkError;

/**
 * Created by victor on 25/03/2017.
 */

public interface RxResponse<T> {
    void onNext(T result);
    void onError(NetWorkError error);
    void onComplete();
}
