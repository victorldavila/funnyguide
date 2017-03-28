package com.victorldavila.funnyguide.view.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.FunnyGuideApp;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.FrescoHelper;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.presenters.MovieDetailPresenter;
import com.victorldavila.funnyguide.repository.MovieRepositoryImp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailMovieActivity extends AppCompatActivity implements DetailActivityView{

    public static final String MOVIE_ITEM = "MOVIE_ITEM";
    public static final String TV_ITEM = "TV_ITEM";

    @BindView(R.id.collapsingToolbar) CollapsingToolbarLayout collapsingToolbarLayout;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        unbinder = ButterKnife.bind(this);

        configFrescoTransition();

        FunnyApi api = ((FunnyGuideApp)getApplication()).getFunnyApi();
        presenter = new MovieDetailPresenter(new MovieRepositoryImp(api));
        presenter.addView(this);

        getBundleInfo();

        presenter.onCreate();
    }

    private void configFrescoTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
                    ScalingUtils.ScaleType.CENTER_CROP));

            getWindow().setSharedElementReturnTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void setImageUrlPoster(String urlPoster) {
        imagePosterMovie.setController(FrescoHelper.loadImage(urlPoster, null));
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
}
