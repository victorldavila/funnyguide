package com.victorldavila.funnyguide.presenters;


import com.victorldavila.funnyguide.api.ErrorHandler;
import com.victorldavila.funnyguide.models.NetWorkError;
import com.victorldavila.funnyguide.models.Season;
import com.victorldavila.funnyguide.repository.TvRepository;
import com.victorldavila.funnyguide.view.fragments.TvFragmentView;
import com.victorldavila.funnyguide.view.fragments.TvSeasonFragmentView;

import java.util.List;

import rx.Subscription;

public class TvSeasonPresenterImp extends BaseRxPresenter implements FragmentPresenter<TvSeasonFragmentView>{
  private TvRepository tvRepository;
  private TvSeasonFragmentView view;

  private Subscription tvSeasonSubscription;

  public TvSeasonPresenterImp(TvRepository tvRepository){
    this.tvRepository = tvRepository;
  }

  @Override
  public void addView(TvSeasonFragmentView view) {
    this.view = view;
  }

  @Override
  public void onViewCreated() {
    if (view != null) {
      getTvSeason();
    }
  }

  @Override
  public void onDestroyView() {
    rxUnSubscribe();
  }

  public void getTvSeason(){
    rxUnSubscribe(tvSeasonSubscription);

    if(tvRepository != null) {
      tvSeasonSubscription = tvRepository.getTv(view.getTvId())
          .map(responseTvs -> responseTvs.getSeasons())
          .doOnError(throwable -> view.setLoadRecycler(false))
          .doOnCompleted(() -> view.setLoadRecycler(false))
          .subscribe(
              this::resultSeasonTv,
              this::errorGetTvRated
          );

      addSubscription(tvSeasonSubscription);
    }
  }

  private void errorGetTvRated(Throwable throwable) {
    NetWorkError error = ErrorHandler.parseError(throwable);
    view.onError(error.getStatus_message());
  }

  private void resultSeasonTv(List<Season> seasonList) {
    view.onItemList(seasonList);
  }
}
