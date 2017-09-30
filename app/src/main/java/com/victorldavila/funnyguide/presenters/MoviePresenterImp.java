package com.victorldavila.funnyguide.presenters;

import com.victorldavila.funnyguide.api.ErrorHandler;
import com.victorldavila.funnyguide.models.NetWorkError;
import com.victorldavila.funnyguide.repository.MovieRepository;
import com.victorldavila.funnyguide.view.fragments.MovieFragmentView;

import rx.Subscription;

public class MoviePresenterImp extends BaseRxPresenter implements FragmentPresenter<MovieFragmentView> {
  private MovieFragmentView view;
  private MovieRepository movieRepository;

  private Subscription movieSubscription;

  private int page;

  public MoviePresenterImp(MovieRepository movieRepository){
    this.movieRepository = movieRepository;
  }

  @Override
  public void addView(MovieFragmentView view){
        this.view = view;
    }

  @Override
  public void onViewCreated() {
    initPage();

    if (view != null) {
      getMoviesGenre();
    }
  }

  @Override
  public void onDestroyView() {
        rxUnSubscribe();
    }

  private void initPage() {
        this.page = 1;
    }

  public void getMoviesGenre(){
    rxUnSubscribe(getMovieSubscription());

    if(movieRepository != null) {
      movieSubscription = movieRepository.getMovieListGenre(view.getGenreId(), page)
        .doOnSubscribe(() -> view.setLoadRecycler(true))
        .doOnError(throwable -> view.setLoadRecycler(false))
        .doOnCompleted(() -> view.setLoadRecycler(false))
        .subscribe(
          movieResponseListItem -> view.onItemList(movieResponseListItem.getResults()),
          this::errormovieListGenre,
          this::countPage
        );

      addSubscription(getMovieSubscription());
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
        getMoviesGenre();
      }
    }
  }

  public Subscription getMovieSubscription() {
        return movieSubscription;
    }
}
