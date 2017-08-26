package com.victorldavila.funnyguide;

import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.NetWorkError;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.presenters.MoviePresenterImp;
import com.victorldavila.funnyguide.presenters.NullViewException;
import com.victorldavila.funnyguide.repository.MovieRepository;
import com.victorldavila.funnyguide.view.fragments.MovieFragmentView;

import org.junit.Before;
import org.junit.Test;

import rx.Subscription;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieTest {

  MovieRepository movieRepositoryMock;
  MovieFragmentView viewMock;

 // ArgumentCaptor<RxResponse> rxResponseArgumentCaptor = ArgumentCaptor.forClass(RxResponse.class);

  MoviePresenterImp moviePresenter;

  Subscription subscriptionMock;

  @Before
  public void setup() {
    movieRepositoryMock = mock(MovieRepository.class);
    viewMock = mock(MovieFragmentView.class);
    subscriptionMock = mock(Subscription.class);

    moviePresenter = new MoviePresenterImp(movieRepositoryMock);
    moviePresenter.addView(viewMock);

   // when(movieRepositoryMock.getMovieListGenre(anyInt(), anyInt(), any(RxResponse.class))).thenReturn(subscriptionMock);

  }

  @Test(expected = NullViewException.class)
  public void onCreateViewWithNullMovieFragmentView() {
  //  moviePresenter.addView(null);
  //  moviePresenter.onViewCreated();
  }

  @Test
  public void onCreateViewOkCallWithNullSubscription() {
  //  moviePresenter.setMovieSubscription(null);
   // moviePresenter.onViewCreated();

   // verify(movieRepositoryMock, times(1)).getMovieListGenre(anyInt(), anyInt(), any(RxResponse.class));
  }

  @Test
  public void onCreateViewOkCallWithValidSubscription() {
   // when(subscriptionMock.isUnsubscribed()).thenReturn(false);

 //   moviePresenter.setMovieSubscription(subscriptionMock);
 //   moviePresenter.onViewCreated();

   // verify(movieRepositoryMock, times(1)).getMovieListGenre(anyInt(), anyInt(), any(RxResponse.class));
  }

  @Test
  public void verifyScrolledWithPositiveScrollAndNotLastItemInGrid() {
    getSpyPresenter();

    moviePresenter.verifyScrolled(1, 4, 2, 1);

    verify(moviePresenter, times(0)).getMoviesGenre();
  }

  @Test
  public void verifyScrollWithNegativeOrZeroScrollPosition() {
    getSpyPresenter();

    moviePresenter.verifyScrolled(1, 4, 2, 0);

    verify(moviePresenter, times(0)).getMoviesGenre();
  }

  @Test
  public void verifyScrollWithPositiveScrollPositionAndLastItemInGrid() {
    //moviePresenter.verifyScrolled(1, 2, 4, 1);

   // verify(movieRepositoryMock, times(1)).getMovieListGenre(anyInt(), anyInt(), any(RxResponse.class));
  }

  @Test(expected = NullViewException.class)
  public void verifyScrollWithPositiveScrollPositionAndLastItemInGridWithNullView() {
    moviePresenter.addView(null);
    moviePresenter.verifyScrolled(1, 2, 4, 1);
  }

  @Test
  public void onNextCallInPresenterWithValidViewWhenGetMovieListGenreIsCall() {
    getSpyPresenter();
    //moviePresenter.getMoviesGenre();

  //  verify(movieRepositoryMock, times(1)).getMovieListGenre(anyInt(), anyInt(), rxResponseArgumentCaptor.capture());

    ResponseListItem<Movie> responseListItemMock = mock(ResponseListItem.class);
  //  rxResponseArgumentCaptor.getValue().onNext(responseListItemMock);

  //  verify(moviePresenter, times(1)).onNext(any());
  }

  @Test(expected = NullViewException.class)
  public void onNextCallInPresenterWithNullViewWhenGetMovieListGenreIsCall() {
    getSpyPresenter();
   // moviePresenter.getMoviesGenre();

  //  verify(movieRepositoryMock, times(1)).getMovieListGenre(anyInt(), anyInt(), rxResponseArgumentCaptor.capture());

    moviePresenter.addView(null);

    ResponseListItem<Movie> responseListItemMock = mock(ResponseListItem.class);
  //  rxResponseArgumentCaptor.getValue().onNext(responseListItemMock);
  }

  @Test
  public void onErrorCallInPresenterWithValidViewWhenGetMovieListGenreIsCall() {
    getSpyPresenter();
    //moviePresenter.getMoviesGenre();

   // verify(movieRepositoryMock, times(1)).getMovieListGenre(anyInt(), anyInt(), rxResponseArgumentCaptor.capture());

    NetWorkError errorMock = mock(NetWorkError.class);
   // rxResponseArgumentCaptor.getValue().onError(errorMock);

   // verify(moviePresenter, times(1)).onError(any());
  }

  @Test(expected = NullViewException.class)
  public void onErrorCallInPresenterWithNullViewWhenGetMovieListGenreIsCall() {
    getSpyPresenter();
 //   moviePresenter.getMoviesGenre();

  //  verify(movieRepositoryMock, times(1)).getMovieListGenre(anyInt(), anyInt(), rxResponseArgumentCaptor.capture());

    moviePresenter.addView(null);

    NetWorkError errorMock = mock(NetWorkError.class);
   // rxResponseArgumentCaptor.getValue().onError(errorMock);
  }

  @Test
  public void onCompleteCallInPresenterWithValidViewWhenGetMovieListGenreIsCall() {
    getSpyPresenter();
    moviePresenter.getMoviesGenre();

  //  verify(movieRepositoryMock, times(1)).getMovieListGenre(anyInt(), anyInt(), rxResponseArgumentCaptor.capture());

   // rxResponseArgumentCaptor.getValue().onComplete();

  //  verify(moviePresenter, times(1)).onComplete();
  }

  @Test(expected = NullViewException.class)
  public void onCompleteCallInPresenterWithNullViewWhenGetMovieListGenreIsCall() {
    getSpyPresenter();
    //moviePresenter.getMoviesGenre();

   // verify(movieRepositoryMock, times(1)).getMovieListGenre(anyInt(), anyInt(), rxResponseArgumentCaptor.capture());

    moviePresenter.addView(null);

   // rxResponseArgumentCaptor.getValue().onComplete();
  }

  @Test
  public void onDestroyViewCall() {
    getSpyPresenter();

    moviePresenter.onDestroyView();

    verify(moviePresenter, times(1)).rxUnSubscribe();
  }

  @Test
  public void verifysetGenreId() {
  //  moviePresenter.setGenreId(35);
  //  assertEquals(moviePresenter.getGenreId(), 35);
  }

  @Test
  public void verifyGenreIdNull() {
   // assertEquals(moviePresenter.getGenreId(), 0);
  }

  private void getSpyPresenter() {
    moviePresenter = spy(new MoviePresenterImp(movieRepositoryMock));
    moviePresenter.addView(viewMock);
  }
}
