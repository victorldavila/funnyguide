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
import com.victorldavila.funnyguide.models.ResponseTv;
import com.victorldavila.funnyguide.presenters.InfoTvPresenterImp;
import com.victorldavila.funnyguide.repository.TvRepository;
import com.victorldavila.funnyguide.repository.TvRepositoryImp;
import com.victorldavila.funnyguide.view.activities.DetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InfoTvFragment extends Fragment implements InfoTvFragmentView{

  @BindView(R.id.neste_scroll) NestedScrollView nestedScrollView;
  @BindView(R.id.overview_info_poster) TextView overviewTv;
  @BindView(R.id.name_info_poster) TextView titleTv;
  @BindView(R.id.rating_info_poster) TextView rateTv;
  @BindView(R.id.original_title_info_poster) TextView originalTitleTv;
  @BindView(R.id.language_info_poster) TextView languageTv;
  @BindView(R.id.genre_info_poster) TextView genreTv;
  @BindView(R.id.status_item) TextView statusTv;
  @BindView(R.id.production_companies) TextView productionCompanies;
  @BindView(R.id.countries) TextView productionCountries;
  @BindView(R.id.release_date_info_poster) TextView releaseDateTv;
  @BindView(R.id.last_air_date_info_poster) TextView lastAirDateTv;
  @BindView(R.id.number_of_episodes) TextView numberOfEpisodesTv;
  @BindView(R.id.number_of_seasons) TextView numberOfSeasonsTv;

  private InfoTvPresenterImp infoTvPresenterImp;

  private Unbinder unbinder;

  private ResponseTv responseTv;

  public InfoTvFragment() {
    // Required empty public constructor
  }

  public static InfoTvFragment newInstance(ResponseTv tv) {
    InfoTvFragment fragment = new InfoTvFragment();
    Bundle args = new Bundle();
    args.putParcelable(DetailActivity.TV_ITEM, tv);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    createTransition();

    super.onCreate(savedInstanceState);

    getExtras();

    configPresenter();
  }

  private void configPresenter() {
    FunnyApi funnyApi = ((FunnyGuideApp) getActivity().getApplication()).getFunnyApi();
    TvRepository tvRepository = new TvRepositoryImp(funnyApi);
    infoTvPresenterImp = new InfoTvPresenterImp(tvRepository);
    infoTvPresenterImp.addView(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_info_tv, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    unbinder = ButterKnife.bind(this, view);

    setInfoItem();

    infoTvPresenterImp.onViewCreated();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    unbinder.unbind();
  }

  private void getExtras() {
    if (getArguments() != null) {
      ResponseTv responseTv = getArguments().getParcelable(DetailActivity.TV_ITEM);

      if (responseTv != null) {
        this.responseTv = responseTv;
      }
    }
  }

  private void setInfoItem() {
    if(responseTv != null) {
      setOverViewInfo(responseTv.getOverview());
      setTitleInfo(responseTv.getName());
      setRateInfo(String.valueOf(responseTv.getVote_average()));
      setDateInfo(responseTv.getFirst_air_date());
      setOriginalTitleInfo(responseTv.getOriginal_name());
    }
  }

  @Override
  public void setOverViewInfo(String overview) {
    overviewTv.setText(overview);
  }

  @Override
  public void setTitleInfo(String title) {
    titleTv.setText(title);
  }

  @Override
  public void setOriginalTitleInfo(String originalTitle) {
    originalTitleTv.setText(originalTitle);
  }

  @Override
  public void setRateInfo(String rate) {
    rateTv.setText(rate);
  }

  @Override
  public void setDateInfo(String date) {
    releaseDateTv.setText(date);
  }

  @Override
  public void setLanguageInfo(String language) {
    languageTv.setText(language);
  }

  @Override
  public void setGenreInfo(String genre) {
    genreTv.setText(genre);
  }

  @Override
  public void setStatus(String status) {
    statusTv.setText(status);
  }

  @Override
  public void setProductionCountries(String countries) {
    productionCountries.setText(countries);
  }

  @Override
  public void setProductionCompanies(String companies) {
    productionCompanies.setText(companies);
  }

  @Override
  public void setLastAirDate(String lastAirDate) {
    lastAirDateTv.setText(lastAirDate);
  }

  @Override
  public void setNumberOfEpisodes(int numberOfEpisodes) {
    numberOfEpisodesTv.setText(String.valueOf(numberOfEpisodes));
  }

  @Override
  public void setNumberOfSeason(int numberOfSeason) {
    numberOfSeasonsTv.setText(String.valueOf(numberOfSeason));
  }

  @Override
  public ResponseTv getResponseTv() {
    return responseTv;
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
