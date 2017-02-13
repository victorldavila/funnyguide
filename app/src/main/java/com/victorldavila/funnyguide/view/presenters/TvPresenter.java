package com.victorldavila.funnyguide.view.presenters;

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
import com.victorldavila.funnyguide.adapter.TvGridAdapter;
import com.victorldavila.funnyguide.adapter.viewholders.LoadPosterViewHolder;
import com.victorldavila.funnyguide.adapter.viewholders.PosterViewHolder;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.Tv;
import com.victorldavila.funnyguide.view.OnViewListener;
import com.victorldavila.funnyguide.view.presenters.interactors.MovieInteractor;
import com.victorldavila.funnyguide.view.presenters.interactors.TvInteractor;

import java.util.ArrayList;

import rx.Subscription;

/**
 * Created by victor on 18/12/2016.
 */
public class TvPresenter implements OnFragmentPresenterListener{

    private final String TAG = this.getClass().getSimpleName();

    private OnViewListener<Tv> view;
    private TvInteractor interactor;

    private ArrayList<Subscription> subscriptions;
    private Subscription movieSubscribe;

    private int page;

    public TvPresenter(OnViewListener<Tv> view, FunnyApi api){
        this.view = view;
        this.page = 1;

        subscriptions = new ArrayList<Subscription>();
        interactor = new TvInteractor(view, this, api);
    }

    @Override
    public void onStop() {
        rxUnSubscribe();
    }

    @Override
    public void onStart() {
        this.page = 1;
        getTvTopRated();
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

    public void getTvTopRated(){
        rxUnSubscribe(movieSubscribe);
        if(interactor != null)
            movieSubscribe = interactor.getTvTopRated(page);
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

    public void loadImage(SimpleDraweeView simpleDraweeView, Tv item, final ProgressBar load){

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
    public void bind() {
        if(interactor != null)
            interactor.bind();
    }

    @Override
    public void unbind() {
        if(interactor != null)
            interactor.unbind();
    }

    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType){
        if(viewType == TvGridAdapter.ITEM_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_layout, parent, false);
            return new PosterViewHolder(view);
        } else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster_load, parent, false);
            return new LoadPosterViewHolder(view);
        }
    }

    public int getItemViewType(int position, int itemCount){
        if(position == (itemCount - 1))
            return TvGridAdapter.FOOTER_TYPE;
        else
            return TvGridAdapter.ITEM_TYPE;
    }

    public void countPage() {
        page++;
    }

    public void bindTvAdapter(RecyclerView.ViewHolder holder, ArrayList<Tv>items, int position, OnBindTvGridAdapter listener){
        if(holder instanceof PosterViewHolder){
            PosterViewHolder posterViewHolder = (PosterViewHolder) holder;
            listener.setInfoTv(posterViewHolder, items.get(position));
        } else if(holder instanceof LoadPosterViewHolder){
            LoadPosterViewHolder loadPosterViewHolder = (LoadPosterViewHolder) holder;
            if(isLoad())
                listener.onEnableLoad(loadPosterViewHolder);
            else
                listener.onDisableLoad(loadPosterViewHolder);
        }
    }

    public void verifyScroll(int dy, int dx, RecyclerView recyclerView) {
        if(dy > 0){ //check for scroll down
            int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
            int totalItemCount = recyclerView.getLayoutManager().getItemCount();
            int pastVisiblesItems = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

            if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                getTvTopRated();
            }
        }
    }

    public interface OnBindTvGridAdapter {
        void setInfoTv(PosterViewHolder posterViewHolder, Tv movie);
        void onEnableLoad(LoadPosterViewHolder loadPosterViewHolder);
        void onDisableLoad(LoadPosterViewHolder loadPosterViewHolder);
    }
}
