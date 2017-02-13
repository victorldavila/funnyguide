package com.victorldavila.funnyguide.api;

import android.util.LruCache;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 09/12/2016.
 */

public class FunnyApi {

    public class QueryString{
        public static final String API_KEY = "api_key";
        public static final String LANGUAGE = "language";

        public static final String TMDB_KEY = "fcfc8516fa3b8989e68936634bc5ea84";
        public static final String LANGUAGE_BR = "pt-br";
        public static final String PAGE = "page";
    }

    public class EndPoint{
        public static final String GENRE = "genre";
        public static final String MOVIE = "movie";
        public static final String MOVIES = "movies";
        public static final String LIST = "list";
        public static final String TV = "tv";
        public static final String TOP_RATED = "top_rated";
    }

    private static final String BASE_URL_TMDB = "https://api.themoviedb.org/3/";
    public static final String BASE_URL_IMAGE_TMDB = "https://image.tmdb.org/t/p/w500";

    private final TmdbAPI tmdbApi;

    private LruCache<Class<?>, Observable<?>> apiObservables = new LruCache<>(10);

    public FunnyApi() {
        this(BASE_URL_TMDB);
    }

    public FunnyApi(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        tmdbApi = retrofit.create(TmdbAPI.class);
    }

    public TmdbAPI getAPI() {
        return tmdbApi;
    }

    public Observable<?> getPreparedObservable(Observable<?> unPreparedObservable, Class<?> clazz, boolean cacheObservable, boolean useCache){
        Observable<?> preparedObservable = null;

        if(useCache) //this way we don't reset anything in the cache if this is the only instance of us not wanting to use it.
            preparedObservable = apiObservables.get(getClass());

        if(preparedObservable!=null)
            return preparedObservable;

        //we are here because we have never created this observable before or we didn't want to use the cache...

        preparedObservable = unPreparedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        if(cacheObservable){
            preparedObservable = preparedObservable.cache();
            apiObservables.put(clazz, preparedObservable);
        }

        return preparedObservable;
    }

    public Map<String, String> getQueryString(){
        Map<String, String> options = new HashMap<String, String>();
        options.put(QueryString.LANGUAGE, QueryString.LANGUAGE_BR);
        options.put(QueryString.API_KEY, QueryString.TMDB_KEY);

        return options;
    }

    public Map<String, String> getQueryStringList(int page){
        Map<String, String> options = getQueryString();
        options.put(QueryString.PAGE, String.valueOf(page));

        return options;
    }
}
