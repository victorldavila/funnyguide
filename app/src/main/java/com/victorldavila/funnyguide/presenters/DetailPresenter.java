package com.victorldavila.funnyguide.presenters;

import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.Tv;
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
    verifyNullView();
    getDetailInfo();
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
    Tv tv = view.getTv();

    movieSubscription = tvRepository.getTv(tv.getId())
      .subscribe(
        tvItem -> {},
        throwable -> {}
      );
  }

  private void getMovieInfo() {
    Movie movie = view.getMovie();

    movieSubscription = movieRepository.getMovie(movie.getId())
      .subscribe(
        movieItem -> {},
        throwable -> {}
      );
  }

  private void verifyNullView() {
    if(view == null)
      throw new NullViewException();
  }
}
