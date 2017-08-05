package com.victorldavila.funnyguide.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.victorldavila.funnyguide.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchToolbar extends Toolbar {

  @BindView(R.id.toolbar) Toolbar toolbar;

  @BindView(R.id.reveal_layout) RelativeLayout revealView;

  @BindView(R.id.toolbar_back_button) ImageButton backButton;
  @BindView(R.id.toolbar_search_button) ImageButton searchButton;
  @BindView(R.id.toolbar_search_view) EditText searchView;

  private int defaultBackgroundColor = 0;
  private int revealBackgroundColor = 0;

  private boolean isExpanded = false;
  private boolean hasBackButton = false;

  public SearchToolbar(Context context) {
    super(context);

    initView();
  }

  public SearchToolbar(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);

    initView();
  }

  public SearchToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    initView();
  }

  public void initView() {
    inflate(getContext(), R.layout.search_toolbar, this);

    ButterKnife.bind(this);
  }

  @OnClick(R.id.toolbar_search_button)
  public void clickSearchButton(View view) {
    startRevealAnimation();
  }

  @OnClick(R.id.toolbar_back_button)
  public void clickBackButton(View view) {
    backRevealAnimation();
  }

  private void startRevealAnimation() {
    float x = searchButton.getX() + searchButton.getWidth() / 2;
    float y = searchButton.getY() + searchButton.getHeight() / 2;

    reveal(x, y, revealBackgroundColor);
  }

  private void reveal(float x, float y, int revealColor) {
    int finalRadius = (int) Math.hypot(toolbar.getWidth(), toolbar.getHeight());

    Animator animation;
    if (revealColor == revealBackgroundColor) {
      animation = ViewAnimationUtils.createCircularReveal(revealView, (int) x, (int) y, 0f, finalRadius);
    } else {
      animation = ViewAnimationUtils.createCircularReveal(revealView, (int) x, (int) y, 0f, finalRadius);
    }
    animation.setDuration(500);
    animation.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationStart(Animator animation) {
        if (hasBackButton) {
          backButton.setVisibility(VISIBLE);
        }

        if (revealColor == revealBackgroundColor) {
          searchView.setVisibility(VISIBLE);
        }
      }

      @Override
      public void onAnimationEnd(Animator animator) {
        toolbar.setBackgroundColor(revealColor);
        revealView.setBackground(null);

        if (!isExpanded) {
          enableSearchView();
        } else {
          isExpanded = false;
        }
      }
    });

    revealView.setBackgroundColor(revealColor);

    animation.start();
  }

  public void backRevealAnimation() {
    disableSearchView();
  }

  private void enableSearchView() {
    searchButton.setVisibility(GONE);

    isExpanded = true;
  }

  private void disableSearchView() {
    if (hasBackButton) {
      backButton.setVisibility(GONE);
    }

    searchView.setVisibility(GONE);
    searchButton.setVisibility(VISIBLE);

    int x = (backButton.getLeft() + backButton.getRight()) / 2;
    int y = (backButton.getTop() + backButton.getBottom()) / 2;

    reveal(x, y, defaultBackgroundColor);
  }

  public void setToolbarBackgroundColor(int color) {
    defaultBackgroundColor = color;

    toolbar.setBackgroundColor(color);
  }

  public void setToolbarRevealBackgroundColor(int color) {
    revealBackgroundColor = color;
  }

  public boolean isExpanded() {
    return isExpanded;
  }

  public void enableBackToolbar() {
    hasBackButton = true;

    final Drawable upArrow = ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back_white);
    upArrow.setColorFilter(defaultBackgroundColor, PorterDuff.Mode.SRC_ATOP);
    backButton.setImageDrawable(upArrow);
  }
}
