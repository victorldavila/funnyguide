package com.victorldavila.funnyguide.view.activities;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.view.MenuItem;
import android.view.Window;

import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.presenters.MainPresenterImp;
import com.victorldavila.funnyguide.view.SearchToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainActivityView{
  @BindView(R.id.search_toolbar) SearchToolbar searchToolbar;
  @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigation;

  private MainPresenterImp presenter;
  private Unbinder unbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.home_activity);

    unbinder = ButterKnife.bind(this);

    initViews();

    configPresenter();
  }

  private void configPresenter() {
    presenter = new MainPresenterImp();
    presenter.addView(this);
    presenter.onCreate();
  }

  private void initViews() {
    searchToolbar.setToolbarBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
    searchToolbar.setToolbarRevealBackgroundColor(Color.WHITE);
    searchToolbar.enableBackToolbar();
    bottomNavigation.setOnNavigationItemSelectedListener(this);
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    return presenter.fragmentChange(item.getItemId());
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    unbinder.unbind();
  }

  @Override
  public void changeFragment(Fragment fragment) {
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    ft.replace(R.id.content, fragment);
    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    ft.commit();
  }

  @Override
  public void onBackPressed() {
    if (searchToolbar.isExpanded()) {
      searchToolbar.backRevealAnimation();
    } else {
      super.onBackPressed();
    }
  }
}
