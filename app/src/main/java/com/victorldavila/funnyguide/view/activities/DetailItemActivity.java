package com.victorldavila.funnyguide.view.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.victorldavila.funnyguide.R;

public class DetailItemActivity extends AppCompatActivity {

    public static final String MOVIE_ITEM = "MOVIE_ITEM";
    public static final String TV_ITEM = "TV_ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
    }

}
