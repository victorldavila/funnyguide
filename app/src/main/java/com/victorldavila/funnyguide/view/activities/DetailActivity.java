package com.victorldavila.funnyguide.view.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.FrescoHelper;
import com.victorldavila.funnyguide.FunnyGuideApp;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.Tv;
import com.victorldavila.funnyguide.presenters.DetailPresenter;
import com.victorldavila.funnyguide.repository.MovieRepository;
import com.victorldavila.funnyguide.repository.MovieRepositoryImp;
import com.victorldavila.funnyguide.repository.TvRepository;
import com.victorldavila.funnyguide.repository.TvRepositoryImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailActivity extends AppCompatActivity implements DetailActivityView{

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

    private DetailPresenter presenter;

    private Movie movie;
    private Tv tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setWindowConfig();

        createTransition();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        unbinder = ButterKnife.bind(DetailActivity.this);

        configFrescoTransition();

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                initActivity();
            }
        });

        setExitSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onSharedElementEnd(List<String> names, List<View> elements, List<View> snapshots) {
                        super.onSharedElementEnd(names, elements, snapshots);
                        initActivity();
                    }
                }
        );
    }

    private void initActivity() {
        createTransition();

        FunnyApi funnyApi = ((FunnyGuideApp)getApplication()).getFunnyApi();

        MovieRepository movieRepository = new MovieRepositoryImp(funnyApi);
        TvRepository tvRepository = new TvRepositoryImp(funnyApi);

        getExtras(movieRepository, tvRepository);

        presenter.addView(DetailActivity.this);
        presenter.onCreate();

        setInfoItem();
    }

    private void getExtras(MovieRepository movieRepository, TvRepository tvRepository) {
        if(getIntent().getExtras() != null) {
            Movie movie = getIntent().getExtras().getParcelable(MOVIE_ITEM);
            if (movie != null) {
                presenter = new DetailPresenter(movieRepository);
                this.movie = movie;
            } else {
                Tv tv = getIntent().getExtras().getParcelable(TV_ITEM);
                presenter = new DetailPresenter(tvRepository);
                this.tv = tv;
            }
        }
    }

    private void setInfoItem() {
        if(movie != null) {
            setImageUrlPoster(movie.getPoster_path());
            setOverViewInfo(movie.getOverview());
            setTitleInfo(movie.getTitle());
            setRateInfo(String.valueOf(movie.getVote_average()));
            setDateInfo(movie.getRelease_date());
            setOriginalTitleInfo(movie.getOriginal_title());
        } else {
            setImageUrlPoster(tv.getPoster_path());
            setOverViewInfo(tv.getOverview());
            setTitleInfo(tv.getName());
            setRateInfo(String.valueOf(tv.getVote_average()));
            setDateInfo(tv.getFirst_air_date());
            setOriginalTitleInfo(tv.getOriginal_name());
        }
    }

    private void setWindowConfig() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
    }

    private void configFrescoTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
                    ScalingUtils.ScaleType.CENTER_CROP));

            getWindow().setSharedElementReturnTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
                    ScalingUtils.ScaleType.CENTER_CROP));
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

    @Override
    public Movie getMovie() {
        return movie;
    }

    @Override
    public Tv getTv() {
        return tv;
    }

    private void createTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide()
                    .setDuration(500)
                    .excludeTarget(R.id.toolbar, true)
                    .excludeTarget(R.id.collapsingToolbarLayout, true)
                    .excludeTarget(android.R.id.statusBarBackground, true)
                    .excludeTarget(android.R.id.navigationBarBackground, true));

            getWindow().setReturnTransition(new android.transition.Fade()
                    .setDuration(300)
                    .excludeTarget(R.id.toolbar, true)
                    .excludeTarget(R.id.collapsingToolbarLayout, true)
                    .excludeTarget(android.R.id.statusBarBackground, true)
                    .excludeTarget(android.R.id.navigationBarBackground, true));
        }
    }
}
