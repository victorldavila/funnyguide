package com.victorldavila.funnyguide.api;

import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.ResponseGenre;
import com.victorldavila.funnyguide.models.ResponseListItem;
import com.victorldavila.funnyguide.models.Tv;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by victor on 09/12/2016.
 */

public interface TmdbAPI {

    @GET(FunnyApi.EndPoint.GENRE + "/" + FunnyApi.EndPoint.MOVIE + "/" + FunnyApi.EndPoint.LIST)
    Observable<ResponseGenre> getGenreObservable(@QueryMap Map<String, String> options);

    @GET(FunnyApi.EndPoint.GENRE + "/{genre_id}/" + FunnyApi.EndPoint.MOVIES)
    Observable<ResponseListItem<Movie>> getMoviesGenreObservable(@Path("genre_id") int genreId, @QueryMap Map<String, String> options);

    @GET(FunnyApi.EndPoint.MOVIES + "/{movie_id}")
    Observable<Movie> getMovieObservable(@Path("movie_id") int movieId);

    @GET(FunnyApi.EndPoint.TV + "/" + FunnyApi.EndPoint.TOP_RATED)
    Observable<ResponseListItem<Tv>> getSeriesTopRateObservable(@QueryMap Map<String, String> options);

    @GET(FunnyApi.EndPoint.TV + "/{tv_id}")
    Observable<Tv> getSerieObservable(@Path("tv_id") int tvId);
}
