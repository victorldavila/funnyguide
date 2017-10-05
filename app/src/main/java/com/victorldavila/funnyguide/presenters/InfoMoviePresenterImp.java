package com.victorldavila.funnyguide.presenters;

import android.text.TextUtils;

import com.victorldavila.funnyguide.models.ResponseMovie;
import com.victorldavila.funnyguide.models.ResponseTv;
import com.victorldavila.funnyguide.repository.MovieRepository;
import com.victorldavila.funnyguide.repository.TvRepository;
import com.victorldavila.funnyguide.view.activities.DetailActivityView;
import com.victorldavila.funnyguide.view.fragments.InfoMovieFragment;

import java.text.DateFormat;

import rx.Observable;
import rx.Subscription;

public class InfoMoviePresenterImp extends BaseRxPresenter implements FragmentPresenter<InfoMovieFragment> {
  private static final String DEFAULT_STRING = "Sem informação";

  private InfoMovieFragment view;
  private MovieRepository movieRepository;

  private Subscription movieSubscription;

  public InfoMoviePresenterImp(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public void addView(InfoMovieFragment view) {
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
    if(movieRepository != null){
      rxUnSubscribe(movieSubscription);

      movieSubscription = getMovieInfo();

      addSubscription(movieSubscription);
    }
  }

  private String resultInfo(String info) {
    return TextUtils.isEmpty(info) ? DEFAULT_STRING : info;
  }

  private Subscription getMovieInfo() {
    ResponseMovie responseMovie = view.getResponseMovie();

    return movieRepository.getMovie(responseMovie.getId())
      .subscribe(
        this::resultMovie,
        throwable -> {}
      );
  }

  private void resultMovie(ResponseMovie responseMovie) {
    view.setTitleInfo(resultInfo(responseMovie.getTitle()));
    view.setOriginalTitleInfo(resultInfo(responseMovie.getOriginal_title()));
    view.setDateInfo(resultInfo(responseMovie.getRelease_date()));
    view.setRateInfo(resultInfo(String.valueOf(responseMovie.getVote_average())));
    view.setStatus(resultInfo(responseMovie.getStatus()));

    Observable.from(responseMovie.getGenres())
      .map(genre -> genre.getName())
      .reduce((name, accumulator) -> accumulator.concat(", " + name))
      .subscribe(
        view::setGenreInfo,
        throwable -> view.setGenreInfo(resultInfo(""))
      );

    Observable.from(responseMovie.getSpoken_languages())
      .map(language -> language.getName())
      .reduce((name, accumulator) -> accumulator.concat(", " + name))
      .subscribe(
        view::setLanguageInfo,
        throwable -> view.setLanguageInfo(resultInfo(""))
      );

    Observable.from(responseMovie.getProduction_companies())
      .map(company -> company.getName())
      .reduce((name, accumulator) -> accumulator.concat(", " + name))
      .subscribe(
        view::setProductionCompanies,
        throwable -> view.setProductionCompanies(resultInfo(""))
      );

    Observable.from(responseMovie.getProduction_countries())
      .map(country -> country.getName())
      .reduce((name, accumulator) -> accumulator.concat(", " + name))
      .subscribe(
        view::setProductionCountries,
        throwable -> view.setProductionCountries(resultInfo(""))
      );
  }
}
