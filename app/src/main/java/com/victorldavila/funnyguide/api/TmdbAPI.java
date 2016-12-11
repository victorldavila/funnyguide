package com.victorldavila.funnyguide.api;

import com.victorldavila.funnyguide.api.FunnyApi;
import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.models.ResponseTmdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by victor on 09/12/2016.
 */

public interface TmdbAPI {

    @GET(FunnyApi.EndPoint.GENRE + "/" + FunnyApi.EndPoint.MOVIE + "/" + FunnyApi.EndPoint.LIST)
    Observable<ResponseTmdb> getGenreObservable(@QueryMap Map<String, String> options);
}
