package com.victorldavila.funnyguide;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.victorldavila.funnyguide.api.FunnyApi;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FunnyGuideApp extends Application {
  private FunnyApi funnyApi;

  @Override
  public void onCreate() {
    super.onCreate();

    funnyApi = new FunnyApi();

    Fresco.initialize(this);
  }

  public FunnyApi getFunnyApi() {
        return funnyApi;
    }
}
