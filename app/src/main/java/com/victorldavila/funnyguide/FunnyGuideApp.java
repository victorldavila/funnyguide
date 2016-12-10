package com.victorldavila.funnyguide;

import android.app.Application;

import com.victorldavila.funnyguide.api.FunnyApi;

/**
 * Created by victor on 09/12/2016.
 */

public class FunnyGuideApp extends Application {

    private FunnyApi funnyApi;

    @Override
    public void onCreate() {
        super.onCreate();

        funnyApi = new FunnyApi();
    }

    public FunnyApi getFunnyApi() {
        return funnyApi;
    }
}
