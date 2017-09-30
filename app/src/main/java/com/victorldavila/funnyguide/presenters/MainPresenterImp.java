package com.victorldavila.funnyguide.presenters;

import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.view.activities.MainActivityView;
import com.victorldavila.funnyguide.view.fragments.GenreFragment;
import com.victorldavila.funnyguide.view.fragments.TvFragment;

public class MainPresenterImp implements ActivityPresenter<MainActivityView> {
  private MainActivityView view;

  @Override
  public void onCreate() {
        fragmentChange(R.id.action_movies);
    }

  @Override
  public void onDestroy() { }

  public boolean fragmentChange(int id) {
    if (view != null) {
      switch (id) {
        case R.id.action_movies:
          view.changeFragment(GenreFragment.newInstance());
          return true;
        case R.id.action_series:
          view.changeFragment(TvFragment.newInstance());
          return true;
        case R.id.action_profile:
          view.changeFragment(TvFragment.newInstance());
          return true;
      }
    }

    return false;
  }

  @Override
  public void addView(MainActivityView view) {
        this.view = view;
    }
}
