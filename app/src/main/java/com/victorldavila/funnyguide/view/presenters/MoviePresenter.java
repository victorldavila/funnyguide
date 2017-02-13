package com.victorldavila.funnyguide.view.presenters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.MovieGridAdapter;
import com.victorldavila.funnyguide.adapter.viewholders.LoadPosterViewHolder;
import com.victorldavila.funnyguide.adapter.viewholders.PosterViewHolder;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.view.OnViewListener;
import com.victorldavila.funnyguide.view.presenters.interactors.GenreInteractor;
import com.victorldavila.funnyguide.view.presenters.interactors.MovieInteractor;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by victo on 12/12/2016.
 */

public class MoviePresenter implements OnFragmentPresenterListener {

    private final String TAG = this.getClass().getSimpleName();

    private OnViewListener<Movie> view;
    private MovieInteractor interactor;

    private ArrayList<Subscription> subscriptions;
    private Subscription movieSubscribe;

    private int genreId;
    private int page;

    public MoviePresenter(OnViewListener<Movie> view, FunnyApi api){
        this.view = view;
        this.page = 1;

        subscriptions = new ArrayList<Subscription>();
        interactor = new MovieInteractor(view, this, api);
    }

    @Override
    public void onStop() {
        rxUnSubscribe();
    }

    @Override
    public void onStart() {
        this.page = 1;
        getMoviesGenre();
    }

    public boolean isLoad() {
        return interactor.isLoad();
    }

    public String getText(String text) {
        if(!TextUtils.isEmpty(text))
            return text;
        else
            return "";
    }

    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType){
        if(viewType == MovieGridAdapter.ITEM_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_layout, parent, false);
            return new PosterViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster_load, parent, false);
            return new LoadPosterViewHolder(view);
        }
    }

    public int getItemViewType(int position, int itemCount){
        if(position == (itemCount - 1))
            return MovieGridAdapter.FOOTER_TYPE;
        else
            return MovieGridAdapter.ITEM_TYPE;
    }

    public void getMoviesGenre(){
        rxUnSubscribe(movieSubscribe);
        if(interactor != null)
            movieSubscribe = interactor.getMoviesGenre(genreId, page);
    }

    private void rxUnSubscribe(Subscription subscription){
        if(subscription!=null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    public void rxUnSubscribe(){
        for (Subscription subscription : subscriptions) {
            if(subscription!=null && !subscription.isUnsubscribed())
                subscription.unsubscribe();
        }
    }

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

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    @Override
    public void bind() {
        if(interactor != null)
            interactor.bind();
    }

    @Override
    public void unbind() {
        if(interactor != null)
            interactor.unbind();
    }

    public void countPage() {
        page++;
    }

    public void verifyScrolled(RecyclerView recyclerView, int dx, int dy) {
        if(dy > 0){ //check for scroll down
            int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
            int totalItemCount = recyclerView.getLayoutManager().getItemCount();
            int pastVisiblesItems = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

            if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                getMoviesGenre();
            }
        }
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
