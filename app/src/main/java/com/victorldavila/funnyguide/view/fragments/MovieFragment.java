package com.victorldavila.funnyguide.view.fragments;

import android.content.Context;
import android.content.Intent;
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
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Item;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.view.OnViewListener;
import com.victorldavila.funnyguide.view.activities.MainActivity;
import com.victorldavila.funnyguide.view.presenters.MoviePresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment implements OnViewListener<Movie>{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_GENRE_ID = "GENRE_ID";

    private MoviePresenter presenter;

    private RecyclerView movieRecyclerView;
    private CoordinatorLayout coordinatorLayoutMovie;
    private MovieGridAdapter movieGridAdapter;

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
        super.onCreate(savedInstanceState);

        FunnyApi api = ((FunnyGuideApp)getActivity().getApplication()).getFunnyApi();
        presenter = new MoviePresenter(this, api);

        if (getArguments() != null) {
            int genreId = getArguments().getInt(ARG_GENRE_ID);
            presenter.setGenreId(genreId);
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

        initViews(view);

        if(presenter != null)
            presenter.onStart();
    }

    private void initViews(View view) {
        coordinatorLayoutMovie = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout_movie);
        movieRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_movie_item);

        configRecycler();
    }

    private void configRecycler() {
        movieGridAdapter =  new MovieGridAdapter(getActivity(), presenter);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        movieRecyclerView.setAdapter(movieGridAdapter);

        movieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                presenter.verifyScrolled(recyclerView, dx, dy);
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
    public void onItemList(List<Movie> results) {
        movieGridAdapter.addList(results);
        movieGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String error) {
        Snackbar snackbar = Snackbar.make(coordinatorLayoutMovie, error, Snackbar.LENGTH_LONG);
        snackbar.show();

        movieGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        if(presenter != null)
            presenter.onStop();
    }
}
