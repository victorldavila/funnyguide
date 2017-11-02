package com.victorldavila.funnyguide.presenters;

import android.text.TextUtils;

import com.victorldavila.funnyguide.Util.DateUtil;
import com.victorldavila.funnyguide.models.ResponseTv;
import com.victorldavila.funnyguide.repository.TvRepository;
import com.victorldavila.funnyguide.view.fragments.InfoTvFragmentView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;

public class InfoTvPresenterImp extends BaseRxPresenter implements FragmentPresenter<InfoTvFragmentView> {
  private static final String DEFAULT_STRING = "Sem informação";

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
    view.setTitleInfo(resultInfo(responseTv.getName()));
    view.setOriginalTitleInfo(resultInfo(responseTv.getOriginal_name()));
    view.setDateInfo(resultInfo(DateUtil.getFormatDate(responseTv.getFirst_air_date())));
    view.setRateInfo(resultInfo(String.valueOf(responseTv.getVote_average())));
    view.setStatus(resultInfo(responseTv.getStatus()));
    view.setLastAirDate(resultInfo(DateUtil.getFormatDate(responseTv.getLast_air_date())));
    view.setNumberOfEpisodes(responseTv.getNumber_of_episodes());
    view.setNumberOfSeason(responseTv.getNumber_of_seasons());

    Observable.from(responseTv.getGenres())
      .map(genre -> genre.getName())
      .reduce((name, accumulator) -> accumulator.concat(", " + name))
      .subscribe(
        view::setGenreInfo,
            throwable -> view.setGenreInfo(resultInfo(""))
      );

    Observable.from(responseTv.getLanguages())
      .map(language -> language)
      .reduce((name, accumulator) -> accumulator.concat(", " + name))
      .subscribe(
        view::setLanguageInfo,
        throwable -> view.setLanguageInfo(resultInfo(""))
      );

    Observable.from(responseTv.getProduction_companies())
      .map(company -> company.getName())
      .reduce((name, accumulator) -> accumulator.concat(", " + name))
      .subscribe(
        view::setProductionCompanies,
        throwable -> view.setProductionCompanies(resultInfo(""))
      );

    Observable.from(responseTv.getOrigin_country())
      .map(country -> country)
      .reduce((name, accumulator) -> accumulator.concat(", " + name))
      .subscribe(
        view::setProductionCountries,
            throwable -> view.setProductionCountries(resultInfo(""))
      );
  }

  private String resultInfo(String info) {
    return TextUtils.isEmpty(info) ? "Sem informação" : info;
  }
}
