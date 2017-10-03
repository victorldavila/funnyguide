package com.victorldavila.funnyguide.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.victorldavila.funnyguide.models.ResponseMovie;
import com.victorldavila.funnyguide.models.ResponseTv;
import com.victorldavila.funnyguide.view.fragments.InfoMovieFragment;
import com.victorldavila.funnyguide.view.fragments.InfoTvFragment;

import java.util.ArrayList;
import java.util.List;

public class DetailViewPagerAdapter extends FragmentStatePagerAdapter {

  private List<String> title;

  private ResponseMovie movie;
  private ResponseTv tv;

  public DetailViewPagerAdapter(FragmentManager fm) {
    super(fm);

    title = new ArrayList<>();

    title.add("Detalhes");
    title.add("Opiniões");
    title.add("Titulos Semelhantes");
    title.add("Creditos");
  }

  @Override
  public int getCount() {
    return title.size();
  }

  @Override
  public Fragment getItem(int position) {
    if (movie != null)
      return InfoMovieFragment.newInstance(movie);
    else
      return InfoTvFragment.newInstance(tv);
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

