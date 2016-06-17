package com.github.denisura.giphy.data.api;

import com.github.denisura.giphy.BuildConfig;
import com.github.denisura.giphy.data.model.GiphyResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class GiphyApi {

    private final String LOG_TAG = GiphyApi.class.getSimpleName();

    public static final String BASE_URL = "http://api.giphy.com";
    public static final String QUERY_APY_KEY = "api_key";
    final String QUERY_SEARCH = "q";

    private static final String API_KEY = BuildConfig.GIPHY_API_KEY;

    private final GiphyEndpointInterface mApiService;
    private final OkHttpClient mHttpClient;

    private class ApiKeyInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter(QUERY_APY_KEY, API_KEY)
                    .build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }

    public GiphyApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        mHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ApiKeyInterceptor())
                .addInterceptor(logging)
                .build();

        //limit the number of concurrent async calls
        mHttpClient.dispatcher().setMaxRequests(5);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mHttpClient)
                .build();

        mApiService = retrofit.create(GiphyEndpointInterface.class);
    }

    public GiphyResponse search(String query) throws IOException {
        Map<String, String> options = new HashMap<>();
        options.put(QUERY_SEARCH, query.replaceAll("[^a-z0-9]+", "+").toLowerCase());

        Call<GiphyResponse> call = mApiService.getSearchResults(options);
        return call.execute().body();
    }

    public GiphyResponse getTrending() throws IOException {
        Call<GiphyResponse> call = mApiService.getTrending();
        return call.execute().body();
    }

    /**
     * Retrofit Interface
     */
    public interface GiphyEndpointInterface {

        @GET("v1/gifs/search")
        Call<GiphyResponse> getSearchResults(@QueryMap Map<String, String> options);

        @GET("/v1/gifs/trending")
        Call<GiphyResponse> getTrending();
    }
}
