package com.victorldavila.funnyguide.view.presenters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.MovieGridAdapter;
import com.victorldavila.funnyguide.adapter.viewholders.LoadPosterViewHolder;
import com.victorldavila.funnyguide.adapter.viewholders.PosterViewHolder;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.view.OnViewListener;
import com.victorldavila.funnyguide.view.activities.DetailItemActivity;
import com.victorldavila.funnyguide.view.fragments.MovieFragment;
import com.victorldavila.funnyguide.view.presenters.interactors.MovieInteractor;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by victor on 12/12/2016.
 */

public class MoviePresenterImp implements FragmentPresenter {

    private final String TAG = this.getClass().getSimpleName();

    private MovieInteractor interactor;

    private CompositeSubscription compositeSubscription;
    private Subscription movieSubscribe;

    private int genreId;
    private int page;

    public MoviePresenterImp(OnViewListener<Movie> view, FunnyApi api){
        this.view = view;

        compositeSubscription = new CompositeSubscription();
        interactor = new MovieInteractor(api);

        initPage();
    }

    @Override
    public void onViewCreated() {
        initPage();
        getMoviesGenre();
    }

    @Override
    public void onDestroyView() {
        rxUnSubscribe();
    }

    public void verifyArgs(Bundle args){
        if (args != null) {
            int genreId = args.getInt(MovieFragment.ARG_GENRE_ID);
            setGenreId(genreId);
        }
    }

    private void initPage() {
        this.page = 1;
    }

    public String getText(String text) {
        if(!TextUtils.isEmpty(text))
            return text;
        else
            return "";
    }

    public void getMoviesGenre(){
        rxUnSubscribe(movieSubscribe);
        if(interactor != null) {
            movieSubscribe = interactor.getMoviesGenre(genreId, page, );
            compositeSubscription.add(movieSubscribe);
        }
    }

    private void rxUnSubscribe(Subscription subscription){
        if(subscription!=null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    public void rxUnSubscribe(){
        compositeSubscription.unsubscribe();
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

    public void verifyScrolled(int visibleItemCount, int totalItemCount, int pastVisiblesItems, int dx, int dy) {
        if(dy > 0) //check for scroll down
            if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                getMoviesGenre();
    }

    public void bindMovieAdapter(RecyclerView.ViewHolder holder, ArrayList<Movie>items, int position, OnBindMovieGridAdapter listener){
        if(holder instanceof PosterViewHolder){
            PosterViewHolder posterViewHolder = (PosterViewHolder) holder;
            listener.setInfoMovie(posterViewHolder, items.get(position));
        } else if(holder instanceof LoadPosterViewHolder){
            LoadPosterViewHolder loadPosterViewHolder = (LoadPosterViewHolder) holder;
            if(isLoad())
                listener.onEnableLoad(loadPosterViewHolder);
            else
                listener.onDisableLoad(loadPosterViewHolder);
        }
    }

    public interface OnBindMovieGridAdapter {
        void setInfoMovie(PosterViewHolder posterViewHolder, Movie movie);
        void onEnableLoad(LoadPosterViewHolder loadPosterViewHolder);
        void onDisableLoad(LoadPosterViewHolder loadPosterViewHolder);
    }
}
