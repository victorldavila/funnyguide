package com.victorldavila.funnyguide.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.victorldavila.funnyguide.FunnyGuideApp;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.ReviewMovieAdapter;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.ResponseReview;
import com.victorldavila.funnyguide.presenters.ReviewPresenterImp;
import com.victorldavila.funnyguide.repository.MovieRepositoryImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReviewsFragment extends Fragment implements ReviewFragmentView{

  public static final String ARG_MOVIE_ID = "_ID";

  @BindView(R.id.recycler_review) RecyclerView reviewRecyclerView;
  @BindView(R.id.coordinator_layout_review) CoordinatorLayout coordinatorLayoutReview;

  @BindView(R.id.layout_empty_state) LinearLayout emptyStateLayout;

  private ReviewMovieAdapter reviewMovieAdapter;
  private ReviewPresenterImp presenter;
  private Unbinder unbinder;

  private int movieId;

  private boolean finishLoad = false;

  public ReviewsFragment() {
    // Required empty public constructor
  }

  public static ReviewsFragment newInstance(int movieId) {
    ReviewsFragment fragment = new ReviewsFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_MOVIE_ID, movieId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getExtras();
    configPresenter();
  }
  private void configPresenter() {
    FunnyApi api = ((FunnyGuideApp)getActivity().getApplication()).getFunnyApi();

    presenter = new ReviewPresenterImp(new MovieRepositoryImp(api));
    presenter.addView(this);
  }

  private void getExtras() {
    if (getArguments() != null) {
      movieId = getArguments().getInt(ARG_MOVIE_ID);
    }
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_reviews, container, false);
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
    reviewMovieAdapter =  new ReviewMovieAdapter();
    reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    reviewRecyclerView.setAdapter(reviewMovieAdapter);

    reviewRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        presenter.verifyScrolled(reviewRecyclerView.getLayoutManager().getChildCount(),
          reviewRecyclerView.getLayoutManager().getItemCount(),
          ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition(),
          dy);
      }
    });
  }

  @Override
  public void setLoadRecycler(boolean isLoad) {
    if (!finishLoad) {
      reviewMovieAdapter.setLoad(isLoad);
      reviewMovieAdapter.notifyDataSetChanged();
    }
  }

  @Override
  public void enableEmptyState() {
    reviewRecyclerView.setVisibility(View.GONE);
    emptyStateLayout.setVisibility(View.VISIBLE);
  }

  @Override
  public void finishLoadReview() {
    finishLoad = true;
  }

  @Override
  public int getMovieId() {
    return movieId;
  }

  @Override
  public void onItemList(List<ResponseReview> results) {
    reviewMovieAdapter.addList(results);
  }

  @Override
  public void onError(String error) {
    Snackbar snackbar = Snackbar.make(coordinatorLayoutReview, error, Snackbar.LENGTH_LONG);
    snackbar.show();
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    if (isVisibleToUser && reviewMovieAdapter != null) {
      reviewMovieAdapter.notifyDataSetChanged();
    }
  }
}
