package com.victorldavila.funnyguide.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.victorldavila.funnyguide.models.ResponseGenre;
import com.victorldavila.funnyguide.view.fragments.MovieFragment;

import java.util.List;

public class GenreViewPagerAdapter extends FragmentStatePagerAdapter {

  private List<ResponseGenre> genres;

  public GenreViewPagerAdapter(FragmentManager fm, List<ResponseGenre> genres) {
    super(fm);

    this.genres = genres;
  }

  @Override
  public int getCount() {
    return genres.size();
  }

  @Override
  public Fragment getItem(int position) {
    if (genres != null)
      return MovieFragment.newInstance(genres.get(position).getId());
    else
      return MovieFragment.newInstance();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return genres.get(position).getName();
  }
}
