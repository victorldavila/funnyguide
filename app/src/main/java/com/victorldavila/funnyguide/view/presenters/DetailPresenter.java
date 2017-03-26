package com.victorldavila.funnyguide.view.presenters;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.view.activities.DetailActivityView;

/**
 * Created by victor on 14/02/2017.
 */

public class DetailPresenter implements ActivityPresenter<DetailActivityView> {

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void addView(DetailActivityView view) {

    }
}
