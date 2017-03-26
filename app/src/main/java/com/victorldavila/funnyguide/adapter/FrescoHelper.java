package com.victorldavila.funnyguide.adapter;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.victorldavila.funnyguide.api.FunnyApi;

/**
 * Created by victo on 26/03/2017.
 */

public class FrescoHelper {

    @NonNull
    private static BaseControllerListener<ImageInfo> getBaseControllerListener(final ProgressBar load) {
        return new BaseControllerListener<ImageInfo>() {
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
    }

    public static DraweeController loadImage(String posterUrl, final ProgressBar progressBar){

        Uri bmpUri = Uri.parse(FunnyApi.BASE_URL_IMAGE_TMDB + posterUrl);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(bmpUri).build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setControllerListener(FrescoHelper.getBaseControllerListener(progressBar))
                .setAutoPlayAnimations(true)
                // other setters
                .build();

        return controller;
    }
}
