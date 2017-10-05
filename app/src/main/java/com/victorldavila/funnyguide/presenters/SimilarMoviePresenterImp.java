package com.victorldavila.funnyguide.presenters;


import com.victorldavila.funnyguide.api.ErrorHandler;
import com.victorldavila.funnyguide.models.NetWorkError;
import com.victorldavila.funnyguide.repository.MovieRepository;
import com.victorldavila.funnyguide.view.fragments.MovieFragmentView;
import com.victorldavila.funnyguide.view.fragments.SimilarMoviesFragmentView;

import rx.Subscription;

public class SimilarMoviePresenterImp extends BaseRxPresenter implements FragmentPresenter<SimilarMoviesFragmentView> {
  private SimilarMoviesFragmentView view;
  private MovieRepository movieRepository;

  private Subscription similarMovieSubscription;

  private int page;

  public SimilarMoviePresenterImp(MovieRepository movieRepository){
    this.movieRepository = movieRepository;
  }

  @Override
  public void addView(SimilarMoviesFragmentView view){
    this.view = view;
  }

  @Override
  public void onViewCreated() {
    initPage();

    if (view != null) {
      getSimilarMovies();
    }
  }

  @Override
  public void onDestroyView() {
    rxUnSubscribe();
  }

  private void initPage() {
    this.page = 1;
  }

  public void getSimilarMovies(){
    rxUnSubscribe(similarMovieSubscription);

    if(movieRepository != null) {
      similarMovieSubscription = movieRepository.getSimilarMovies(view.getMovieId(), page)
          .map(movieResponseListItem -> movieResponseListItem.getResults())
          .doOnSubscribe(() -> view.setLoadRecycler(true))
          .doOnError(throwable -> view.setLoadRecycler(false))
          .doOnCompleted(() -> view.setLoadRecycler(false))
          .subscribe(
              view::onItemList,
              this::errormovieListGenre,
              this::countPage
          );

      addSubscription(similarMovieSubscription);
    }
  }

  private void errormovieListGenre(Throwable throwable) {
    NetWorkError error = ErrorHandler.parseError(throwable);
    view.onError(error.getStatus_message());
  }

  public void countPage() {
    page++;
  }

  public void verifyScrolled(int visibleItemCount, int totalItemCount, int pastVisiblesItems, int dy) {
    if (dy > 0) {
      if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
        getSimilarMovies();
      }
    }
  }
}
