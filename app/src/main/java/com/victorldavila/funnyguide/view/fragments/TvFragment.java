package com.victorldavila.funnyguide.view.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.FunnyGuideApp;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.TvGridAdapter;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Tv;
import com.victorldavila.funnyguide.repository.TvRepositoryImp;
import com.victorldavila.funnyguide.view.activities.DetailActivity;
import com.victorldavila.funnyguide.presenters.TvPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * Use the {@link TvFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TvFragment extends Fragment implements TvFragmentView {

    @BindView(R.id.recycler_tv_item) RecyclerView tvRecyclerView;
    @BindView(R.id.coordinator_layout_tv) CoordinatorLayout coordinatorLayoutMovie;

    private TvPresenter presenter;
    private TvGridAdapter tvGridAdapter;
    private Unbinder unbinder;

    public TvFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment TvFragment.
     */
    public static TvFragment newInstance() {
        TvFragment fragment = new TvFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FunnyApi api = ((FunnyGuideApp)getActivity().getApplication()).getFunnyApi();

        presenter = new TvPresenter(new TvRepositoryImp(api));
        presenter.addView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
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
        tvGridAdapter =  new TvGridAdapter(this);
        tvRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        tvRecyclerView.setAdapter(tvGridAdapter);

        tvRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                presenter.verifyScroll(tvRecyclerView.getLayoutManager().getChildCount()
                        , tvRecyclerView.getLayoutManager().getItemCount()
                        , ((GridLayoutManager)tvRecyclerView.getLayoutManager()).findFirstVisibleItemPosition()
                        , dy);
            }
        });
    }

    @Override
    public void onItemList(List<Tv> results) {
        tvGridAdapter.addList(results);
    }

    @Override
    public void onError(String error) {
        Snackbar snackbar = Snackbar.make(coordinatorLayoutMovie, error, Snackbar.LENGTH_LONG);
        snackbar.show();

        tvGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void changeActivity(Tv tv, SimpleDraweeView image) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.TV_ITEM, tv);

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
        tvGridAdapter.setLoad(isLoad);
        tvGridAdapter.notifyDataSetChanged();
    }
}
