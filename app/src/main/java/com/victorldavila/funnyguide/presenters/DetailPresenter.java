package com.victorldavila.funnyguide.presenters;

import android.support.annotation.NonNull;

import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.NetWorkError;
import com.victorldavila.funnyguide.models.Tv;
import com.victorldavila.funnyguide.repository.MovieRepository;
import com.victorldavila.funnyguide.repository.TvRepository;
import com.victorldavila.funnyguide.view.activities.DetailActivityView;

import rx.Subscription;

/**
 * Created by victo on 27/03/2017.
 */

public class DetailPresenter extends BaseRxPresenter implements ActivityPresenter<DetailActivityView> {

    private DetailActivityView view;
    private MovieRepository movieRepository;
    private TvRepository tvRepository;

    private Subscription movieSubscription;

    private Movie movie;
    private Tv tv;

    public DetailPresenter(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public DetailPresenter(TvRepository tvRepository) {
        this.tvRepository = tvRepository;
    }

    @Override
    public void addView(DetailActivityView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        if(view == null)
            throw new NullViewException();

        setInfoItem();
        getDetailInfo();
    }

    private void setInfoItem() {
        if(movie != null) {
            view.setImageUrlPoster(movie.getPoster_path());
            view.setOverViewInfo(movie.getOverview());
            view.setTitleInfo(movie.getTitle());
            view.setRateInfo(String.valueOf(movie.getVote_average()));
            view.setDateInfo(movie.getRelease_date());
            view.setOriginalTitleInfo(movie.getOriginal_title());
        } else {
            view.setImageUrlPoster(tv.getPoster_path());
            view.setOverViewInfo(tv.getOverview());
            view.setTitleInfo(tv.getName());
            view.setRateInfo(String.valueOf(tv.getVote_average()));
            view.setDateInfo(tv.getFirst_air_date());
            view.setOriginalTitleInfo(tv.getOriginal_name());
        }
    }

    @Override
    public void onDestroy() {
        rxUnSubscribe();
    }

    public void getDetailInfo(){
        if(movieRepository != null){
            rxUnSubscribe(movieSubscription);
            if (movieRepository != null) {
                movieSubscription = movieRepository.getMovie(movie.getId(), getMovieRxResponse());
            } else {
                movieSubscription = tvRepository.getTv(tv.getId(), getTvRxResponse());
            }
            addSubscription(movieSubscription);
        }
    }

    @NonNull
    private RxResponse<Tv> getTvRxResponse() {
        return new RxResponse<Tv>() {
            @Override
            public void onNext(Tv result) {

            }

            @Override
            public void onError(NetWorkError error) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @NonNull
    private RxResponse<Movie> getMovieRxResponse() {
        return new RxResponse<Movie>() {
            @Override
            public void onNext(Movie result) {

            }

            @Override
            public void onError(NetWorkError error) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setTv(Tv tv) {
        this.tv = tv;
    }
}
