package com.victorldavila.funnyguide;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.victorldavila.funnyguide.api.FunnyApi;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by victor on 09/12/2016.
 */

public class FunnyGuideApp extends Application {

    private FunnyApi funnyApi;

    @Override
    public void onCreate() {
        super.onCreate();

        funnyApi = new FunnyApi();

        Fresco.initialize(this);

        /*Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);*/
    }

    public FunnyApi getFunnyApi() {
        return funnyApi;
    }
}
