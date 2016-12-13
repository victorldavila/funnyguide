package com.victorldavila.funnyguide.api;

import com.victorldavila.funnyguide.models.ResponseGenre;

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

    @GET(FunnyApi.EndPoint.GENRE + "/{genre_id}" + FunnyApi.EndPoint.MOVIES)
    Observable<ResponseGenre> getMoviesGenreObservable(@Path("genre_id") int genreId, @QueryMap Map<String, String> options);
}
