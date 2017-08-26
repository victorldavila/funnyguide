package com.victorldavila.funnyguide.presenters;

import com.victorldavila.funnyguide.api.ErrorHandler;
import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.models.NetWorkError;
import com.victorldavila.funnyguide.models.ResponseGenre;
import com.victorldavila.funnyguide.repository.GenreRepository;
import com.victorldavila.funnyguide.view.ResponseView;

import rx.Subscription;

public class GenrePresenter extends BaseRxPresenter implements FragmentPresenter<ResponseView<Genre>> {
  private ResponseView<Genre> view;
  private GenreRepository genreRepository;

  private Subscription genreSubscription;

  public GenrePresenter(GenreRepository genreRepository){
    this.genreRepository = genreRepository;
  }

  @Override
  public void onViewCreated() {
    verifyNullView();
    getGenre();
  }

  private void verifyNullView() {
    if(view == null)
      throw new NullViewException();
  }

  @Override
  public void onDestroyView() {
        rxUnSubscribe();
    }

  public void getGenre(){
        rxUnSubscribe(genreSubscription);
        if(genreRepository != null) {
            genreSubscription = genreRepository.getMovieGenre()
            .subscribe(responseGenre -> {
                  verifyNullView();
                  view.onItemList(responseGenre.getGenres());
              },
              throwable -> {
                  verifyNullView();
                  NetWorkError error = ErrorHandler.parseError(throwable);
                  view.onError(error.getStatus_message());
            });
            addSubscription(genreSubscription);
        }
    }

    @Override
    public void addView(ResponseView<Genre> view) {
        this.view = view;
    }
}
