package com.victorldavila.funnyguide.view.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victorldavila.funnyguide.FunnyGuideApp;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.MovieGridAdapter;
import com.victorldavila.funnyguide.adapter.TvGridAdapter;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.Tv;
import com.victorldavila.funnyguide.view.OnViewListener;
import com.victorldavila.funnyguide.view.presenters.MoviePresenter;
import com.victorldavila.funnyguide.view.presenters.TvPresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * Use the {@link TvFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TvFragment extends Fragment implements OnViewListener<Tv> {

    private TvPresenter presenter;

    private RecyclerView tvRecyclerView;
    private CoordinatorLayout coordinatorLayoutMovie;
    private TvGridAdapter tvGridAdapter;

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
        presenter = new TvPresenter(this, api);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }



    private void initViews(View view) {
        coordinatorLayoutMovie = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout_tv);
        tvRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_tv_item);

        configRecycler();
    }

    private void configRecycler() {
        tvGridAdapter =  new TvGridAdapter(getActivity(), presenter);
        tvRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        tvRecyclerView.setAdapter(tvGridAdapter);

        tvRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) //check for scroll down
                {
                    int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    int pastVisiblesItems = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        presenter.getTvTopRated();
                    }
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemList(List<Tv> results) {
        tvGridAdapter.addList(results);
        tvGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String error) {
        Snackbar snackbar = Snackbar.make(coordinatorLayoutMovie, error, Snackbar.LENGTH_LONG);
        snackbar.show();

        tvGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(presenter != null)
            presenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(presenter != null)
            presenter.onStop();
    }
}
