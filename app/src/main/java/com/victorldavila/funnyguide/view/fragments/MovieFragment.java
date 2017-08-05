package com.victorldavila.funnyguide.view.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.FunnyGuideApp;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.MovieGridAdapter;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.repository.MovieRepositoryImp;
import com.victorldavila.funnyguide.view.activities.DetailActivity;
import com.victorldavila.funnyguide.presenters.MoviePresenterImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment implements MovieFragmentView {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_GENRE_ID = "GENRE_ID";

    @BindView(R.id.recycler_movie_item) RecyclerView movieRecyclerView;
    @BindView(R.id.coordinator_layout_movie) CoordinatorLayout coordinatorLayoutMovie;

    private MovieGridAdapter movieGridAdapter;
    private MoviePresenterImp presenter;
    private Unbinder unbinder;

    private int genreId;

    public MovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param genreId Parameter 1.
     * @return A new instance of fragment MovieSerieFragment.
     */
    public static MovieFragment newInstance(int genreId) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_GENRE_ID, genreId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment MovieSerieFragment.
     */
    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
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

        presenter = new MoviePresenterImp(new MovieRepositoryImp(api));
        presenter.addView(this);
    }

    private void getExtras() {
        if (getArguments() != null) {
            genreId = getArguments().getInt(MovieFragment.ARG_GENRE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_serie, container, false);
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
        movieGridAdapter =  new MovieGridAdapter(this);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        movieRecyclerView.setAdapter(movieGridAdapter);

        movieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                presenter.verifyScrolled(movieRecyclerView.getLayoutManager().getChildCount()
                        , movieRecyclerView.getLayoutManager().getItemCount()
                        , ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition()
                        , dy);
            }
        });
    }

    @Override
    public void changeActivity(Movie movie, SimpleDraweeView image){
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE_ITEM, movie);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(getActivity()
                            , image
                            , getString(R.string.poster_transition));

            startActivity(intent, options.toBundle());
        } else
            startActivity(intent);
    }

    @Override
    public void setLoadRecycler(boolean isLoad) {
        movieGridAdapter.setLoad(isLoad);

        movieGridAdapter.notifyDataSetChanged();
    }

    @Override
    public int getGenreId() {
        return genreId;
    }

    @Override
    public void onItemList(List<Movie> results) {
        movieGridAdapter.addList(results);
    }

    @Override
    public void onError(String error) {
        Snackbar snackbar = Snackbar.make(coordinatorLayoutMovie, error, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void createReenterTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setReenterTransition(new Fade()
                    .setDuration(300)
                    .excludeTarget(R.id.toolbar, true)
                    .excludeTarget(R.id.collapsingToolbarLayout, true)
                    .excludeTarget(android.R.id.statusBarBackground, true)
                    .excludeTarget(android.R.id.navigationBarBackground, true));

            getActivity().getWindow().setExitTransition(new Fade()
                    .setDuration(300)
                    .excludeTarget(R.id.toolbar, true)
                    .excludeTarget(R.id.collapsingToolbarLayout, true)
                    .excludeTarget(android.R.id.statusBarBackground, true)
                    .excludeTarget(android.R.id.navigationBarBackground, true));

            getActivity().getWindow().setSharedElementExitTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
                    ScalingUtils.ScaleType.CENTER_CROP));

            getActivity().getWindow().setSharedElementReenterTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
                    ScalingUtils.ScaleType.CENTER_CROP));
        }
    }



    @Override
    public void startActivity(Intent intent) {
        setExitSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onSharedElementEnd(List<String> names, List<View> elements, List<View> snapshots) {
                        super.onSharedElementEnd(names, elements, snapshots);
                        movieGridAdapter.notifyDataSetChanged();
                    }
                }
        );
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && movieGridAdapter != null) {
            movieGridAdapter.notifyDataSetChanged();
        }
    }
}
