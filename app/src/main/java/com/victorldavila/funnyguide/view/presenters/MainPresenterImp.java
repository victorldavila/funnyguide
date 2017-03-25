package com.victorldavila.funnyguide.view.presenters;


import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.view.activities.MainActivityView;
import com.victorldavila.funnyguide.view.fragments.GenreFragment;
import com.victorldavila.funnyguide.view.fragments.TvFragment;

/**
 * Created by victor on 10/12/2016.
 */

public class MainPresenterImp implements ActivityPresenter {

    private MainActivityView view;

    public MainPresenterImp() {

    }

    @Override
    public void onCreate() {
        fragmentChange(R.id.action_movies);
    }

    @Override
    public void onDestroy() {

    }

    public boolean fragmentChange(int id) {
        if(view == null)
            throw new NullViewException();

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
            default:
                return false;
        }
    }

    public void setView(MainActivityView view) {
        this.view = view;
    }
}
