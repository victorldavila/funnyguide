package com.victorldavila.funnyguide.view.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.victorldavila.funnyguide.FunnyGuideApp;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.TvSeasonGridAdapter;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Season;
import com.victorldavila.funnyguide.presenters.TvSeasonPresenterImp;
import com.victorldavila.funnyguide.repository.TvRepositoryImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TvSeasonsFragment extends Fragment implements TvSeasonFragmentView{

  public static final String ARG_TV_ID = "TV_ID";

  @BindView(R.id.recycler_season) RecyclerView seasonRecyclerView;
  @BindView(R.id.coordinator_layout_season) CoordinatorLayout coordinatorLayoutMovie;

  private TvSeasonGridAdapter tvSeasonGridAdapter;
  private TvSeasonPresenterImp presenter;
  private Unbinder unbinder;

  private int tvId;

  public TvSeasonsFragment() { }

  public static TvSeasonsFragment newInstance(int tvId) {
    TvSeasonsFragment fragment = new TvSeasonsFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_TV_ID, tvId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    createReenterTransition();

    super.onCreate(savedInstanceState);

    getExtras();
    configPresenter();
  }

  private void configPresenter() {
    FunnyApi api = ((FunnyGuideApp)getActivity().getApplication()).getFunnyApi();

    presenter = new TvSeasonPresenterImp(new TvRepositoryImp(api));
    presenter.addView(this);
  }

  private void getExtras() {
    if (getArguments() != null) {
      tvId = getArguments().getInt(ARG_TV_ID);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_tv_seasons, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    unbinder = ButterKnife.bind(this, view);

    configRecycler();

    presenter.onViewCreated();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    unbinder.unbind();

    presenter.onDestroyView();
  }

  private void configRecycler() {
    tvSeasonGridAdapter =  new TvSeasonGridAdapter();
    seasonRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    seasonRecyclerView.setAdapter(tvSeasonGridAdapter);

    seasonRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        presenter.verifyScroll(seasonRecyclerView.getLayoutManager().getChildCount()
            , seasonRecyclerView.getLayoutManager().getItemCount()
            , ((GridLayoutManager)seasonRecyclerView.getLayoutManager()).findFirstVisibleItemPosition()
            , dy);
      }
    });
  }

  @Override
  public void setLoadRecycler(boolean isLoad) {
    tvSeasonGridAdapter.setLoad(isLoad);
    tvSeasonGridAdapter.notifyDataSetChanged();
  }

  @Override
  public int getTvId() {
    return tvId;
  }

  @Override
  public void onItemList(List<Season> results) {
    tvSeasonGridAdapter.addList(results);
  }

  @Override
  public void onError(String error) {
    Snackbar snackbar = Snackbar.make(coordinatorLayoutMovie, error, Snackbar.LENGTH_LONG);
    snackbar.show();
  }

  private void createReenterTransition() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      configSharedElementExit();
      configSharedElementReenter();
    }
  }

  private void configSharedElementReenter() {
    getActivity()
        .getWindow()
        .setSharedElementReenterTransition(DraweeTransition
            .createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
                ScalingUtils.ScaleType.CENTER_CROP));
  }

  private void configSharedElementExit() {
    getActivity()
        .getWindow()
        .setSharedElementExitTransition(DraweeTransition
            .createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
                ScalingUtils.ScaleType.CENTER_CROP));
  }

  @Override
  public void startActivity(Intent intent) {
    setExitSharedElementCallback(
        new SharedElementCallback() {
          @Override
          public void onSharedElementEnd(List<String> names, List<View> elements, List<View> snapshots) {
            super.onSharedElementEnd(names, elements, snapshots);
            tvSeasonGridAdapter.notifyDataSetChanged();
          }
        }
    );
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    if (isVisibleToUser && tvSeasonGridAdapter != null) {
      tvSeasonGridAdapter.notifyDataSetChanged();
    }
  }
}
