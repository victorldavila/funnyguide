package com.victorldavila.funnyguide.presenters;

import com.victorldavila.funnyguide.models.ResponseTv;
import com.victorldavila.funnyguide.repository.TvRepository;
import com.victorldavila.funnyguide.view.fragments.InfoTvFragmentView;

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
    /*view.setTitleInfo(responseMovie.getTitle());
    view.setOriginalTitleInfo(responseMovie.getOriginal_title());
    view.setDateInfo(responseMovie.getRelease_date());
    view.setRateInfo(String.valueOf(responseMovie.getVote_average()));
    view.setStatus(responseMovie.getStatus());

    Observable.from(responseMovie.getGenres())
        .map(genre -> genre.getName())
        .reduce((name, accumulator) -> accumulator.concat(", " + name))
        .subscribe(view::setGenreInfo);

    Observable.from(responseMovie.getSpoken_languages())
        .map(language -> language.getName())
        .reduce((name, accumulator) -> accumulator.concat(", " + name))
        .subscribe(view::setLanguageInfo);

    Observable.from(responseMovie.getProduction_companies())
        .map(company -> company.getName())
        .reduce((name, accumulator) -> accumulator.concat(", " + name))
        .subscribe(view::setProductionCompanies);

    Observable.from(responseMovie.getProduction_countries())
        .map(country -> country.getName())
        .reduce((name, accumulator) -> accumulator.concat(", " + name))
        .subscribe(view::setProductionCountries);*/
  }
}
