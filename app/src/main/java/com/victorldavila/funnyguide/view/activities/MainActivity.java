package com.victorldavila.funnyguide.view.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.view.presenters.MainPresenter;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        presenter = new MainPresenter(this);
        presenter.fragmentChange(MainPresenter.FIRST_FRAGMENT);
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
