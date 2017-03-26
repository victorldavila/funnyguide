package com.victorldavila.funnyguide.presenters;

import com.victorldavila.funnyguide.models.NetWorkError;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.models.Tv;
import com.victorldavila.funnyguide.repository.TvRepository;
import com.victorldavila.funnyguide.view.fragments.TvFragmentView;

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
