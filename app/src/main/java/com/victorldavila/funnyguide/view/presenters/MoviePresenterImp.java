package com.victorldavila.funnyguide.view.presenters;

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
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.NetWorkError;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.repository.MovieRepository;
import com.victorldavila.funnyguide.view.fragments.MovieFragmentView;

import rx.Subscription;

/**
 * Created by victor on 12/12/2016.
 */

public class MoviePresenterImp extends BaseRxPresenter implements FragmentPresenter<MovieFragmentView>, RxResponse<ResponseListItem<Movie>> {

    private MovieFragmentView view;
    private MovieRepository movieRepository;

    private Subscription movieSubscription;

    private int genreId;
    private int page;

    public MoviePresenterImp(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @Override
    public void addView(MovieFragmentView view){
        this.view = view;
    }

    @Override
    public void onViewCreated() {
        if(view == null)
            throw new NullViewException();

        initPage();
        getMoviesGenre();
    }

    @Override
    public void onDestroyView() {
        rxUnSubscribe();
    }

    private void initPage() {
        this.page = 1;
    }

    public void getMoviesGenre(){
        rxUnSubscribe(movieSubscription);
        if(movieRepository != null) {
            view.setLoadRecycler(true);
            movieSubscription = movieRepository.getMovieGenre(genreId, page, this);
            addSubscription(movieSubscription);
        }
    }

    public DraweeController loadImage(String pathPoster, final ProgressBar load){

        Uri bmpUri = Uri.parse(FunnyApi.BASE_URL_IMAGE_TMDB + pathPoster);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(bmpUri).build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setControllerListener(getBaseControllerListener(load))
                .setAutoPlayAnimations(true)
                // other setters
                .build();

        return controller;
    }

    @NonNull
    private BaseControllerListener<ImageInfo> getBaseControllerListener(final ProgressBar load) {
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

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public void countPage() {
        page++;
    }

    public void verifyScrolled(int visibleItemCount, int totalItemCount, int pastVisiblesItems, int dy) {
        if(dy > 0) //check for scroll down
            if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                getMoviesGenre();
    }

    @Override
    public void onNext(ResponseListItem<Movie> result) {
        view.onItemList(result.getResults());
    }

    @Override
    public void onError(NetWorkError error) {
        view.onError(error.getStatus_message());
        view.setLoadRecycler(false);
    }

    @Override
    public void onComplete() {
        countPage();

        view.setLoadRecycler(false);
    }
}
