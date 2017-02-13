package com.victorldavila.funnyguide.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.view.presenters.GenrePresenter;

import java.util.List;

/**
 * Created by victo on 11/12/2016.
 */

public class GenreViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Genre> genres;
    private GenrePresenter presenter;

    public GenreViewPagerAdapter(FragmentManager fm, List<Genre> genres, GenrePresenter presenter) {
        super(fm);

        this.genres = genres;
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return genres.size();
    }

    @Override
    public Fragment getItem(int position) {
        return presenter.getItemGenre(genres, position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return genres.get(position).getName();
    }
}
