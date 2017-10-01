package com.victorldavila.funnyguide.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.victorldavila.funnyguide.models.ResponseGenre;
import com.victorldavila.funnyguide.models.ResponseMovie;
import com.victorldavila.funnyguide.models.ResponseTv;
import com.victorldavila.funnyguide.view.fragments.InfoItemFragment;
import com.victorldavila.funnyguide.view.fragments.MovieFragment;

import java.util.List;

public class DetailViewPagerAdapter extends FragmentStatePagerAdapter {

  private List<String> title;

  private ResponseMovie movie;
  private ResponseTv tv;

  public DetailViewPagerAdapter(FragmentManager fm, List<String> title) {
    super(fm);

    this.title = title;
  }

  @Override
  public int getCount() {
    return title.size();
  }

  @Override
  public Fragment getItem(int position) {
    if (movie != null)
      return InfoItemFragment.newInstance();
    else
      return InfoItemFragment.newInstance();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return title.get(position);
  }

  public void setMovie(ResponseMovie movie) {
    this.movie = movie;
  }

  public void setTv(ResponseTv tv) {
    this.tv = tv;
  }
}

