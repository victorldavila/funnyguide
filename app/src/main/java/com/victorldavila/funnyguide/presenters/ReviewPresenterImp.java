package com.victorldavila.funnyguide.presenters;


import com.victorldavila.funnyguide.api.ErrorHandler;
import com.victorldavila.funnyguide.models.NetWorkError;
import com.victorldavila.funnyguide.models.ResponseReview;
import com.victorldavila.funnyguide.repository.MovieRepository;
import com.victorldavila.funnyguide.view.fragments.ReviewFragmentView;

import java.util.List;

import rx.Subscription;

public class ReviewPresenterImp extends BaseRxPresenter implements FragmentPresenter<ReviewFragmentView> {
  private ReviewFragmentView view;
  private MovieRepository movieRepository;

  private Subscription reviewSubscription;

  private int page;

  public ReviewPresenterImp(MovieRepository movieRepository){
    this.movieRepository = movieRepository;
  }

  @Override
  public void addView(ReviewFragmentView view){
    this.view = view;
  }

  @Override
  public void onViewCreated() {
    initPage();

    if (view != null) {
      getMovieReviews();
    }
  }

  @Override
  public void onDestroyView() {
    rxUnSubscribe();
  }

  private void initPage() {
    this.page = 1;
  }

  public void getMovieReviews(){
    rxUnSubscribe(reviewSubscription);

    if(movieRepository != null) {
      reviewSubscription = movieRepository.getMovieReviews(view.getMovieId(), page)
          .map(responseReviewListItem -> responseReviewListItem.getResults())
        .doOnSubscribe(() -> view.setLoadRecycler(true))
        .doOnError(throwable -> view.setLoadRecycler(false))
        .doOnCompleted(() -> view.setLoadRecycler(false))
        .subscribe(
          this::onNextReview,
          this::errorReviewListGenre,
          this::countPage
        );

      addSubscription(reviewSubscription);
    }
  }

  private void onNextReview(List<ResponseReview> responseReviews) {
    if (responseReviews.size() != 0) {
      view.onItemList(responseReviews);
    } else if (page == 1) {
      view.enableEmptyState();
    } else {
      view.finishLoadReview();
    }
  }

  private void errorReviewListGenre(Throwable throwable) {
    NetWorkError error = ErrorHandler.parseError(throwable);
    view.onError(error.getStatus_message());
  }

  public void countPage() {
    page++;
  }

  public void verifyScrolled(int visibleItemCount, int totalItemCount, int pastVisiblesItems, int dy) {
    if (dy > 0) {
      if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
        getMovieReviews();
      }
    }
  }
}