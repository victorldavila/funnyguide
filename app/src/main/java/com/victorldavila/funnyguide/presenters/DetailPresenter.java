package com.victorldavila.funnyguide.presenters;

import com.victorldavila.funnyguide.models.ResponseMovie;
import com.victorldavila.funnyguide.models.ResponseTv;
import com.victorldavila.funnyguide.repository.MovieRepository;
import com.victorldavila.funnyguide.repository.TvRepository;
import com.victorldavila.funnyguide.view.activities.DetailActivityView;

import rx.Subscription;

public class DetailPresenter extends BaseRxPresenter implements ActivityPresenter<DetailActivityView> {
  private DetailActivityView view;
  private MovieRepository movieRepository;
  private TvRepository tvRepository;

  private Subscription movieSubscription;

  public DetailPresenter(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public DetailPresenter(TvRepository tvRepository) {
        this.tvRepository = tvRepository;
    }

  @Override
  public void addView(DetailActivityView view) {
        this.view = view;
    }

  @Override
  public void onCreate() {
    if (view != null) {
      getDetailInfo();
    }
  }

  @Override
  public void onDestroy() {
        rxUnSubscribe();
    }

  public void getDetailInfo(){
    if(movieRepository != null){
      rxUnSubscribe(movieSubscription);

      if (movieRepository != null) {
        getMovieInfo();
      } else {
        getTvInfo();
      }

      addSubscription(movieSubscription);
    }
  }

  private void getTvInfo() {
    ResponseTv responseTv = view.getResponseTv();

    movieSubscription = tvRepository.getTv(responseTv.getId())
      .subscribe(
        tvItem -> {},
        throwable -> {}
      );
  }

  private void getMovieInfo() {
    ResponseMovie responseMovie = view.getResponseMovie();

    movieSubscription = movieRepository.getMovie(responseMovie.getId())
      .subscribe(
        movieItem -> {},
        throwable -> {}
      );
  }
}
