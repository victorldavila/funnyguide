package com.victorldavila.funnyguide.view.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.victorldavila.funnyguide.FunnyGuideApp;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.ResponseMovie;
import com.victorldavila.funnyguide.models.ResponseTv;
import com.victorldavila.funnyguide.presenters.InfoItemPresenter;
import com.victorldavila.funnyguide.repository.MovieRepository;
import com.victorldavila.funnyguide.repository.MovieRepositoryImp;
import com.victorldavila.funnyguide.repository.TvRepository;
import com.victorldavila.funnyguide.repository.TvRepositoryImp;
import com.victorldavila.funnyguide.view.activities.DetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InfoMovieFragment extends Fragment implements InfoMovieFragmentView{
  @BindView(R.id.neste_scroll) NestedScrollView nestedScrollView;
  @BindView(R.id.overview_info_poster) TextView overviewMovie;
  @BindView(R.id.title_info_poster) TextView titleMovie;
  @BindView(R.id.rating_info_poster) TextView rateMovie;
  @BindView(R.id.release_date_info_poster) TextView dateMovie;
  @BindView(R.id.original_title_info_poster) TextView originalTitleMovie;
  @BindView(R.id.language_info_poster) TextView languageMovie;
  @BindView(R.id.genre_info_poster) TextView genreMovie;

  private InfoItemPresenter infoItemPresenter;

  private Unbinder unbinder;

  private ResponseMovie responseMovie;

  public InfoMovieFragment() {
    // Required empty public constructor
  }

  public static InfoMovieFragment newInstance(ResponseMovie movie) {
    InfoMovieFragment fragment = new InfoMovieFragment();
    Bundle args = new Bundle();
    args.putParcelable(DetailActivity.MOVIE_ITEM, movie);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    createTransition();

    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      getExtras();
    }

    FunnyApi funnyApi = ((FunnyGuideApp) getActivity().getApplication()).getFunnyApi();
    MovieRepository movieRepository = new MovieRepositoryImp(funnyApi);
    infoItemPresenter = new InfoItemPresenter(movieRepository);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_detail_item, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    unbinder = ButterKnife.bind(this, view);

    setInfoItem();

    infoItemPresenter.onViewCreated();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    unbinder.unbind();
  }

  private void getExtras() {
    ResponseMovie responseMovie = getArguments().getParcelable(DetailActivity.MOVIE_ITEM);

    if (responseMovie != null) {
      this.responseMovie = responseMovie;
    }
  }

  private void setInfoItem() {
    if(responseMovie != null) {
      setOverViewInfo(responseMovie.getOverview());
      setTitleInfo(responseMovie.getTitle());
      setRateInfo(String.valueOf(responseMovie.getVote_average()));
      setDateInfo(responseMovie.getRelease_date());
      setOriginalTitleInfo(responseMovie.getOriginal_title());
    }
  }

  @Override
  public void setOverViewInfo(String overview) {
    overviewMovie.setText(overview);
  }

  @Override
  public void setTitleInfo(String title) {
    titleMovie.setText(title);
  }

  @Override
  public void setOriginalTitleInfo(String originalTitle) {
    originalTitleMovie.setText(originalTitle);
  }

  @Override
  public void setRateInfo(String rate) {
    rateMovie.setText(rate);
  }

  @Override
  public void setDateInfo(String date) {
    dateMovie.setText(date);
  }

  @Override
  public void setLanguageInfo(String language) {
    languageMovie.setText(language);
  }

  @Override
  public void setGenreInfo(String genre) {
    genreMovie.setText(genre);
  }

  @Override
  public ResponseMovie getResponseMovie() {
    return responseMovie;
  }

  private void createTransition() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      configEnterTransition();
      configReturnTransition();
    }
  }

  private void configReturnTransition() {
    getActivity()
      .getWindow()
        .setReturnTransition(new android.transition.Fade()
          .setDuration(300));
  }

  private void configEnterTransition() {
    getActivity()
      .getWindow()
        .setEnterTransition(new android.transition.Fade()
          .setDuration(500));
  }
}
