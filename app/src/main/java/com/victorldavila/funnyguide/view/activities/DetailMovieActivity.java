package com.victorldavila.funnyguide.view.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.FunnyGuideApp;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.FrescoHelper;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.presenters.MovieDetailPresenter;
import com.victorldavila.funnyguide.repository.MovieRepository;
import com.victorldavila.funnyguide.repository.MovieRepositoryImp;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailMovieActivity extends AppCompatActivity implements DetailActivityView{

    public static final String MOVIE_ITEM = "MOVIE_ITEM";
    public static final String TV_ITEM = "TV_ITEM";

    @BindView(R.id.collapsingToolbarLayout) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.item_poster_img) SimpleDraweeView imagePosterMovie;

    @BindView(R.id.overview_info_poster) TextView overviewMovie;

    @BindView(R.id.title_info_poster) TextView titleMovie;
    @BindView(R.id.rating_info_poster) TextView rateMovie;
    @BindView(R.id.release_date_info_poster) TextView dateMovie;

    @BindView(R.id.original_title_info_poster) TextView originalTitleMovie;
    @BindView(R.id.language_info_poster) TextView languageMovie;
    @BindView(R.id.genre_info_poster) TextView genreMovie;

    private Unbinder unbinder;

    private MovieDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createTransition();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        unbinder = ButterKnife.bind(this);

        configFrescoTransition();

        FunnyApi funnyApi = ((FunnyGuideApp)getApplication()).getFunnyApi();
        MovieRepository movieRepository = new MovieRepositoryImp(funnyApi);
        presenter = new MovieDetailPresenter(movieRepository);
        presenter.addView(DetailMovieActivity.this);

        getBundleInfo();

        presenter.onCreate();
    }

    private void configFrescoTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
                    ScalingUtils.ScaleType.CENTER_CROP));
        }
    }

    private void getBundleInfo() {
        if(getIntent().getExtras() != null){
            Movie movie = getIntent().getExtras().getParcelable(MOVIE_ITEM);
            presenter.setMovie(movie);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    @Override
    public void setOverViewInfo(String overview) {
        overviewMovie.setText(overview);
    }

    @Override
    public void setTitleInfo(String title) {
        collapsingToolbarLayout.setTitle(title);
        titleMovie.setText(title);
    }

    @Override
    public void setOriginalTitleInfo(String originalTitle) {
        originalTitleMovie.setText(originalTitle);
    }

    @Override
    public void setRateInfo(String rate) {
        rateMovie.setText(rate);
    }

    @Override
    public void setDateInfo(String date) {
        dateMovie.setText(date);
    }

    @Override
    public void setLanguageInfo(String language) {
        languageMovie.setText(language);
    }

    @Override
    public void setGenreInfo(String genre) {
        genreMovie.setText(genre);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createTransition() {
        getWindow().setEnterTransition(new Slide()
                .setDuration(500)
                .excludeTarget(R.id.toolbar, true)
                .excludeTarget(R.id.collapsingToolbarLayout, true)
                .excludeTarget(android.R.id.statusBarBackground, true)
                .excludeTarget(android.R.id.navigationBarBackground, true));
    }
}
