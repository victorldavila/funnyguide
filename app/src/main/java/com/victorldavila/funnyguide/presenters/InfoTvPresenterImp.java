package com.victorldavila.funnyguide.presenters;

import com.victorldavila.funnyguide.models.ResponseTv;
import com.victorldavila.funnyguide.repository.TvRepository;
import com.victorldavila.funnyguide.view.fragments.InfoTvFragmentView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;

public class InfoTvPresenterImp extends BaseRxPresenter implements FragmentPresenter<InfoTvFragmentView> {
  private InfoTvFragmentView view;
  private TvRepository tvRepository;

  private Subscription tvSubscription;

  public InfoTvPresenterImp(TvRepository tvRepository) {
        this.tvRepository = tvRepository;
  }

  @Override
  public void addView(InfoTvFragmentView view) {
    this.view = view;
  }

  @Override
  public void onViewCreated() {
    if (view != null) {
      getDetailInfo();
    }
  }

  @Override
  public void onDestroyView() {
    rxUnSubscribe();
  }

  public void getDetailInfo(){
    if(tvRepository != null){
      rxUnSubscribe(tvSubscription);

      tvSubscription = getTvInfo();

      addSubscription(tvSubscription);
    }
  }

  private Subscription getTvInfo() {
    ResponseTv responseTv = view.getResponseTv();

    return tvRepository.getTv(responseTv.getId())
      .subscribe(
        this::resultTv,
        throwable -> {}
      );
  }

  private void resultTv(ResponseTv responseTv) {
    view.setTitleInfo(responseTv.getName());
    view.setOriginalTitleInfo(responseTv.getOriginal_name());
    view.setDateInfo(responseTv.getFirst_air_date());
    view.setRateInfo(String.valueOf(responseTv.getVote_average()));
    view.setStatus(responseTv.getStatus());
    view.setLastAirDate(responseTv.getLast_air_date());
    view.setNumberOfEpisodes(responseTv.getNumber_of_episodes());
    view.setNumberOfSeason(responseTv.getNumber_of_seasons());

    Observable.from(responseTv.getGenres())
        .map(genre -> genre.getName())
        .reduce((name, accumulator) -> accumulator.concat(", " + name))
        .subscribe(view::setGenreInfo,
            throwable -> { });

    Observable.from(responseTv.getLanguages())
        .map(language -> language)
        .reduce((name, accumulator) -> accumulator.concat(", " + name))
        .subscribe(view::setLanguageInfo,
            throwable -> { });

    Observable.from(responseTv.getProduction_companies())
        .map(company -> company.getName())
        .reduce((name, accumulator) -> accumulator.concat(", " + name))
        .subscribe(view::setProductionCompanies,
            throwable -> { });

    Observable.from(responseTv.getOrigin_country())
        .map(country -> country)
        .reduce((name, accumulator) -> accumulator.concat(", " + name))
        .subscribe(view::setProductionCountries,
            throwable -> { });
  }
}
