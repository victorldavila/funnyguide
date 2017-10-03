package com.victorldavila.funnyguide.presenters;

import com.victorldavila.funnyguide.models.ResponseMovie;
import com.victorldavila.funnyguide.models.ResponseTv;
import com.victorldavila.funnyguide.repository.MovieRepository;
import com.victorldavila.funnyguide.repository.TvRepository;
import com.victorldavila.funnyguide.view.activities.DetailActivityView;
import com.victorldavila.funnyguide.view.fragments.InfoMovieFragment;

import java.text.DateFormat;

import rx.Observable;
import rx.Subscription;

public class InfoItemPresenter extends BaseRxPresenter implements FragmentPresenter<InfoMovieFragment> {
  private InfoMovieFragment view;
  private MovieRepository movieRepository;
  private TvRepository tvRepository;

  private Subscription movieSubscription;

  public InfoItemPresenter(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  /*public InfoItemPresenter(TvRepository tvRepository) {
        this.tvRepository = tvRepository;
    }*/

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

  /*private void getTvInfo() {
    ResponseTv responseTv = view.getResponseTv();

    movieSubscription = tvRepository.getTv(responseTv.getId())
      .subscribe(
        this::resultTv,
        throwable -> {}
      );
  }

  private void resultTv(ResponseTv responseTv) {

  }*/

  private Subscription getMovieInfo() {
    ResponseMovie responseMovie = view.getResponseMovie();

    return movieRepository.getMovie(responseMovie.getId())
      .subscribe(
        this::resultMovie,
        throwable -> {}
      );
  }

  private void resultMovie(ResponseMovie responseMovie) {
    view.setTitleInfo(responseMovie.getTitle());
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
  }
}
