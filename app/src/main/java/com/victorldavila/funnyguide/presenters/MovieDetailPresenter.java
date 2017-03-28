package com.victorldavila.funnyguide.presenters;

import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.NetWorkError;
import com.victorldavila.funnyguide.repository.MovieRepository;
import com.victorldavila.funnyguide.view.activities.DetailActivityView;

import rx.Subscription;

/**
 * Created by victo on 27/03/2017.
 */

public class MovieDetailPresenter extends BaseRxPresenter implements ActivityPresenter<DetailActivityView>,RxResponse<Movie> {

    private DetailActivityView view;
    private MovieRepository movieRepository;

    private Subscription movieSubscription;

    private Movie movie;

    public MovieDetailPresenter(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void addView(DetailActivityView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        if(view == null)
            throw new NullViewException();

        setInfoMovie();
        getMovieInfo();
    }

    private void setInfoMovie() {
        if(movie != null) {
            view.setImageUrlPoster(movie.getPoster_path());
            view.setOverViewInfo(movie.getOverview());
            view.setTitleInfo(movie.getTitle());
            view.setRateInfo(String.valueOf(movie.getVote_average()));
            view.setDateInfo(movie.getRelease_date());
            view.setOriginalTitleInfo(movie.getOriginal_title());
        }
    }

    @Override
    public void onDestroy() {
        rxUnSubscribe();
    }

    @Override
    public void onNext(Movie result) {

    }

    @Override
    public void onError(NetWorkError error) {

    }

    @Override
    public void onComplete() {

    }

    public void getMovieInfo(){
        if(movieRepository != null){
            rxUnSubscribe(movieSubscription);
            movieSubscription = movieRepository.getMovie(movie.getId(), this);
            addSubscription(movieSubscription);
        }
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
