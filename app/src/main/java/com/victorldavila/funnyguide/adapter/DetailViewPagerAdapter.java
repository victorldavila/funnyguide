package com.victorldavila.funnyguide.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.victorldavila.funnyguide.models.ResponseMovie;
import com.victorldavila.funnyguide.models.ResponseTv;
import com.victorldavila.funnyguide.view.fragments.InfoMovieFragment;
import com.victorldavila.funnyguide.view.fragments.InfoTvFragment;
import com.victorldavila.funnyguide.view.fragments.ReviewsFragment;

import java.util.ArrayList;
import java.util.List;

public class DetailViewPagerAdapter extends FragmentStatePagerAdapter {
  private static final int DETALHES = 0;
  private static final int OPINIAO = 1;
  private static final int SEMELHANTES = 2;
  private static final int CREDITOS = 3;

  private List<String> titleMovie;
  private List<String> titleTv;

  private ResponseMovie movie;
  private ResponseTv tv;

  public DetailViewPagerAdapter(FragmentManager fm) {
    super(fm);

    createTitleMovie();
    createTitleTv();
  }

  private void createTitleMovie() {
    titleMovie = new ArrayList<>();

    titleMovie.add("Detalhes");
    titleMovie.add("Opiniões");
    titleMovie.add("Titulos Semelhantes");
    titleMovie.add("Creditos");
  }

  private void createTitleTv() {
    titleTv = new ArrayList<>();

    titleTv.add("Detalhes");
    titleTv.add("Opiniões");
    titleTv.add("Titulos Semelhantes");
    titleTv.add("Creditos");
  }

  @Override
  public int getCount() {
    return movie != null ? titleMovie.size() : titleTv.size();
  }

  @Override
  public Fragment getItem(int position) {
    return movie != null ? getMovieFragment(position) : getTvFragment(position);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return movie != null ? titleMovie.get(position) : titleTv.get(position);
  }

  public void setMovie(ResponseMovie movie) {
    this.movie = movie;
  }

  public void setTv(ResponseTv tv) {
    this.tv = tv;
  }

  private Fragment getMovieFragment(int position) {
    switch (position) {
      case DETALHES: return InfoMovieFragment.newInstance(movie);
      case OPINIAO: return ReviewsFragment.newInstance(movie.getId());
      case SEMELHANTES: return ReviewsFragment.newInstance(movie.getId());
      case CREDITOS: return ReviewsFragment.newInstance(movie.getId());
      default: return ReviewsFragment.newInstance(movie.getId());
    }
  }

  private Fragment getTvFragment(int position) {
    switch (position) {
      case DETALHES: return InfoTvFragment.newInstance(tv);
      case OPINIAO: return InfoTvFragment.newInstance(tv);
      case SEMELHANTES: return InfoTvFragment.newInstance(tv);
      case CREDITOS: return InfoTvFragment.newInstance(tv);
      default: return InfoTvFragment.newInstance(tv);
    }
  }
}

