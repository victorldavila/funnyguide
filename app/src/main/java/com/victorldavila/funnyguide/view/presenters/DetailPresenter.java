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

/**
 * Created by victor on 14/02/2017.
 */

public class DetailPresenter implements OnPresenterListener {

    public void loadImage(SimpleDraweeView simpleDraweeView, Movie item, final ProgressBar load){

        Uri bmpUri = Uri.parse(FunnyApi.BASE_URL_IMAGE_TMDB + item.getPoster_path());
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(bmpUri).build();

        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                load.setVisibility(View.GONE);

                if (imageInfo == null)
                    return;
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                load.setVisibility(View.GONE);
                Log.e(getClass().getSimpleName(), throwable.getMessage());
            }
        };

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setControllerListener(controllerListener)
                .setAutoPlayAnimations(true)
                // other setters
                .build();
        simpleDraweeView.setController(controller);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onStart() {

    }
}
