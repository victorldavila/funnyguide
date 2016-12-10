package com.victorldavila.funnyguide.view.activities;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.victorldavila.funnyguide.FunnyGuideApp;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.api.TmdbAPI;
import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.view.OnViewListener;
import com.victorldavila.funnyguide.view.presenters.GenrePresenter;
import com.victorldavila.funnyguide.view.presenters.MainPresenter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private static final int MAIN_FRAGMENT = 0;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        presenter = new MainPresenter(this);
        presenter.fragmentChange(MAIN_FRAGMENT);
    }

    private void initViews() {
        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return presenter.fragmentChange(item.getItemId());
    }
}
