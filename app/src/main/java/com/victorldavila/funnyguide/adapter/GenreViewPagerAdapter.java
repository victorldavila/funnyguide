package com.victorldavila.funnyguide.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.view.fragments.MovieFragment;

import java.util.List;

/**
 * Created by victo on 11/12/2016.
 */

public class GenreViewPagerAdapter extends FragmentPagerAdapter {

    private List<Genre> genres;

    public GenreViewPagerAdapter(FragmentManager fm, List<Genre> genres) {
        super(fm);
        this.genres = genres;
    }

    @Override
    public int getCount() {
        return genres.size();
    }

    @Override
    public Fragment getItem(int position) {
        return MovieFragment.newInstance();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return genres.get(position).getName();
    }
}
