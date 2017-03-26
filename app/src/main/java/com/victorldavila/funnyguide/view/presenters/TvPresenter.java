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
import com.victorldavila.funnyguide.adapter.FrescoHelper;
import com.victorldavila.funnyguide.adapter.TvGridAdapter;
import com.victorldavila.funnyguide.adapter.viewholders.LoadPosterViewHolder;
import com.victorldavila.funnyguide.adapter.viewholders.PosterViewHolder;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.NetWorkError;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.models.Tv;
import com.victorldavila.funnyguide.repository.TvRepository;
import com.victorldavila.funnyguide.view.OnViewListener;
import com.victorldavila.funnyguide.view.fragments.TvFragmentView;
import com.victorldavila.funnyguide.view.presenters.interactors.TvInteractor;

import java.util.ArrayList;

import rx.Subscription;

/**
 * Created by victor on 18/12/2016.
 */
public class TvPresenter extends BaseRxPresenter implements FragmentPresenter<TvFragmentView>, RxResponse<ResponseListItem<Tv>> {

    private TvRepository tvRepository;
    private TvFragmentView view;

    private Subscription tvSubscription;

    private int page;

    public TvPresenter(TvRepository tvRepository){
        this.tvRepository = tvRepository;
    }

    @Override
    public void onViewCreated() {
        if(view == null)
            throw new NullViewException();

        initPage();
        getTvTopRated();
    }

    @Override
    public void onDestroyView() {
        rxUnSubscribe();
    }

    private void initPage() {
        this.page = 1;
    }

    public void getTvTopRated(){
        rxUnSubscribe(tvSubscription);
        if(tvRepository != null) {
            tvSubscription = tvRepository.getTvTopRated(page, this);
            addSubscription(tvSubscription);
        }
    }

    public void countPage() {
        page++;
    }

    public void verifyScroll(int visibleItemCount, int totalItemCount, int pastVisiblesItems, int dy) {
        if(dy > 0) //check for scroll down
            if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                getTvTopRated();
    }

    @Override
    public void onNext(ResponseListItem<Tv> result) {
        view.onItemList(result.getResults());

        countPage();
    }

    @Override
    public void onError(NetWorkError error) {
        view.onError(error.getStatus_message());
        view.setLoadRecycler(false);
    }

    @Override
    public void onComplete() {
        view.setLoadRecycler(false);
    }

    @Override
    public void addView(TvFragmentView view) {
        this.view = view;
    }
}
