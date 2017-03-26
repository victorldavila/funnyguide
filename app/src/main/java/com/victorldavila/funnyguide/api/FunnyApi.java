package com.victorldavila.funnyguide.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.victorldavila.funnyguide.api.FunnyApi.Header.USER_AGENT;
import static com.victorldavila.funnyguide.api.FunnyApi.Header.USER_AGENT_VALUE;
import static com.victorldavila.funnyguide.api.FunnyApi.QueryString.API_KEY;
import static com.victorldavila.funnyguide.api.FunnyApi.QueryString.TMDB_KEY;

/**
 * Created by victor on 09/12/2016.
 */

public class FunnyApi {

    public class Header{
        protected static final String USER_AGENT = "User-Agent";

        protected static final String USER_AGENT_VALUE = "FunnyGuide";
    }

    public class QueryString{
        protected static final String API_KEY = "api_key";
        public static final String LANGUAGE = "language";

        protected static final String TMDB_KEY = "";
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

    public FunnyApi() {
        this(BASE_URL_TMDB);
    }

    public FunnyApi(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .client(getTmdbClient())
                .build();

        tmdbApi = retrofit.create(TmdbAPI.class);
    }

    private OkHttpClient getTmdbClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> getResponse(chain));
        OkHttpClient client = httpClient.build();
        return client;
    }

    private okhttp3.Response getResponse(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(API_KEY, TMDB_KEY)
                .build();

        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .url(url)
                .header(USER_AGENT, USER_AGENT_VALUE);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

    public TmdbAPI getAPI() {
        return tmdbApi;
    }

    public Map<String, String> getQueryString(){
        Map<String, String> options = new HashMap<String, String>();
        options.put(QueryString.LANGUAGE, QueryString.LANGUAGE_BR);

        return options;
    }

    public Map<String, String> getQueryStringList(int page){
        Map<String, String> options = getQueryString();
        options.put(QueryString.PAGE, String.valueOf(page));

        return options;
    }
}
