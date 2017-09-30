package com.victorldavila.funnyguide.presenters;

import com.victorldavila.funnyguide.api.ErrorHandler;
import com.victorldavila.funnyguide.models.ResponseGenre;
import com.victorldavila.funnyguide.models.NetWorkError;
import com.victorldavila.funnyguide.repository.GenreRepository;
import com.victorldavila.funnyguide.view.ResponseView;

import rx.Subscription;

public class GenrePresenter extends BaseRxPresenter implements FragmentPresenter<ResponseView<ResponseGenre>> {
  private ResponseView<ResponseGenre> view;
  private GenreRepository genreRepository;

  private Subscription genreSubscription;

  public GenrePresenter(GenreRepository genreRepository){
    this.genreRepository = genreRepository;
  }

  @Override
  public void addView(ResponseView<ResponseGenre> view) {
    this.view = view;
  }

  @Override
  public void onViewCreated() {
    if (view != null) {
      getGenre();
    }
  }

  @Override
  public void onDestroyView() {
        rxUnSubscribe();
    }

  public void getGenre(){
        rxUnSubscribe(genreSubscription);

        if(genreRepository != null) {
            genreSubscription = genreRepository.getMovieGenre()
            .subscribe(
              responseGenre -> view.onItemList(responseGenre.getGenres()),
              this::handleError
            );

          addSubscription(genreSubscription);
        }
    }

  private void handleError(Throwable throwable) {
    NetWorkError error = ErrorHandler.parseError(throwable);
    view.onError(error.getStatus_message());
  }
}
