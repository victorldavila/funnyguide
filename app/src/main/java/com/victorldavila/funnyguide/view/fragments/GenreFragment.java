package com.victorldavila.funnyguide.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victorldavila.funnyguide.FunnyGuideApp;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.GenreViewPagerAdapter;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.ResponseGenre;
import com.victorldavila.funnyguide.repository.GenreRepositoryImp;
import com.victorldavila.funnyguide.view.ResponseView;
import com.victorldavila.funnyguide.presenters.GenrePresenterImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GenreFragment extends Fragment implements ResponseView<ResponseGenre> {
  @BindView(R.id.tab_layout_genres) TabLayout tabBarGenre;
  @BindView(R.id.view_pager_movies) ViewPager moviesViewPager;
  @BindView(R.id.coordinator_layout_genre) CoordinatorLayout coordinatorLayoutGenre;

  private GenrePresenterImp presenter;

  private Unbinder unbinder;

  public GenreFragment() { }

  public static GenreFragment newInstance() {
    GenreFragment fragment = new GenreFragment();
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    FunnyApi api = ((FunnyGuideApp) getActivity().getApplication()).getFunnyApi();
    presenter = new GenrePresenterImp(new GenreRepositoryImp(api));
    presenter.addView(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_genre, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    unbinder = ButterKnife.bind(this, view);

    initView();

    presenter.onViewCreated();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    unbinder.unbind();

    presenter.onDestroyView();
  }

  private void initView() {
        tabBarGenre.setupWithViewPager(moviesViewPager);
  }

  @Override
  public void onItemList(List<ResponseGenre> results) {
    GenreViewPagerAdapter adapter = new GenreViewPagerAdapter(getFragmentManager(), results);
    moviesViewPager.setAdapter(adapter);
  }

  @Override
  public void onError(String error) {
    Snackbar snackbar = Snackbar.make(coordinatorLayoutGenre, error, Snackbar.LENGTH_LONG);
    snackbar.show();
  }
}
