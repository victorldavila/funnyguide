package com.victorldavila.funnyguide.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.view.OnViewListener;
import com.victorldavila.funnyguide.view.presenters.GenrePresenter;

import java.util.List;

import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 *
 * Use the {@link GenreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GenreFragment extends Fragment implements OnViewListener<Genre>{

    private GenrePresenter presenter;

    private TabLayout tabBarGenre;
    private ViewPager moviesViewPager;

    public GenreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment GenreFragment.
     */
    public static GenreFragment newInstance() {
        GenreFragment fragment = new GenreFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FunnyApi api = ((FunnyGuideApp) getActivity().getApplication()).getFunnyApi();
        presenter = new GenrePresenter(getActivity(), this, api);
        presenter.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genre, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        tabBarGenre = (TabLayout) view.findViewById(R.id.tab_layout_genres);
        moviesViewPager = (ViewPager) view.findViewById(R.id.view_pager_movies);
        tabBarGenre.setupWithViewPager(moviesViewPager);
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
    public void onItemList(List<Genre> results) {
        GenreViewPagerAdapter adapter = new GenreViewPagerAdapter(getFragmentManager(), results);
        moviesViewPager.setAdapter(adapter);
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(presenter != null)
            presenter.onDestroy();
    }
}
