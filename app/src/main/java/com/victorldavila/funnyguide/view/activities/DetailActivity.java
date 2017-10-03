package com.victorldavila.funnyguide.view.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.FrescoHelper;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.DetailViewPagerAdapter;
import com.victorldavila.funnyguide.models.ResponseMovie;
import com.victorldavila.funnyguide.models.ResponseTv;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailActivity extends AppCompatActivity implements DetailActivityView{
  public static final String MOVIE_ITEM = "MOVIE_ITEM";
  public static final String TV_ITEM = "TV_ITEM";

  @BindView(R.id.coordinator_layout_detail) CoordinatorLayout coordinatorLayout;
  @BindView(R.id.collapsingToolbarLayout) CollapsingToolbarLayout collapsingToolbarLayout;
  @BindView(R.id.app_bar_layout) AppBarLayout appBarLayout;
  @BindView(R.id.tab_layout_details) TabLayout tabBarDetails;
  @BindView(R.id.view_pager_details) ViewPager detailsViewPager;
  @BindView(R.id.item_poster_img) SimpleDraweeView imagePosterMovie;

  private Unbinder unbinder;

  private ResponseMovie responseMovie;
  private ResponseTv responseTv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setWindowConfig();

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_item);

    unbinder = ButterKnife.bind(DetailActivity.this);

    configFrescoTransition();
    configSharedElementCallback();
  }

  private void configSharedElementCallback() {
    setEnterSharedElementCallback(new SharedElementCallback() {
      @Override
      public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
        initActivity();
      }
    });

    setExitSharedElementCallback(new SharedElementCallback() {
      @Override
      public void onSharedElementEnd(List<String> names, List<View> elements, List<View> snapshots) {
        super.onSharedElementEnd(names, elements, snapshots);
        initActivity();
      }
    });
  }

  private void initActivity() {
    getExtras();
    initView();
    setInfoItem();
  }

  private void getExtras() {
    if(getIntent().getExtras() != null) {
      ResponseMovie responseMovie = getIntent().getExtras().getParcelable(MOVIE_ITEM);

      if (responseMovie != null) {
        this.responseMovie = responseMovie;
      } else {
        ResponseTv responseTv = getIntent().getExtras().getParcelable(TV_ITEM);
        this.responseTv = responseTv;
      }
    }
  }

  private void setInfoItem() {
    if(responseMovie != null) {
      setImageUrlPoster(responseMovie.getPoster_path());
    } else {
      setImageUrlPoster(responseTv.getPoster_path());
    }
  }

  private void setWindowConfig() {
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
  }

  private void configFrescoTransition() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      configSharedElementEnter();
      configSharedElementReturn();
    }
  }

  private void configSharedElementReturn() {
    getWindow()
      .setSharedElementReturnTransition(DraweeTransition
        .createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
          ScalingUtils.ScaleType.CENTER_CROP));
  }

  private void configSharedElementEnter() {
    getWindow()
      .setSharedElementEnterTransition(DraweeTransition
        .createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
          ScalingUtils.ScaleType.CENTER_CROP));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    unbinder.unbind();
  }

  @Override
  public void setImageUrlPoster(String urlPoster) {
    imagePosterMovie.setController(FrescoHelper.loadImageTransition(urlPoster, null, () -> supportStartPostponedEnterTransition()));
  }

  private void initView() {
    tabBarDetails.setupWithViewPager(detailsViewPager);

    DetailViewPagerAdapter adapter = new DetailViewPagerAdapter(getSupportFragmentManager());

    if (responseMovie != null) {
      adapter.setMovie(responseMovie);
    } else {
      adapter.setTv(responseTv);
    }

    detailsViewPager.setAdapter(adapter);
  }

  public ResponseMovie getResponseMovie() {
        return responseMovie;
    }

  public ResponseTv getResponseTv() {
        return responseTv;
    }
}
