package com.pnuema.bible.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.pnuema.bible.statics.ConnectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Houses all the api calls to get data
 */
@SuppressWarnings("FieldCanBeLocal")
public final class BiblesOrgAPI {
    @SuppressWarnings("FieldCanBeLocal") private static String baseUrl = "https://bibles.org/v2/";
    @SuppressWarnings("FieldCanBeLocal") private static String apiKey = "joMYGKFIjcYh1KiwYpE3lg08fla0cqEeSaM09II1";
    private static Retrofit retrofit;
    private static OkHttpClient.Builder httpClient;
    private static String CACHE_FOLDER = "http-cache";
    private static String HEADER_CACHE_CONTROL = "Cache-Control";
    private static int CACHE_OFFLINE_DAYS = 365;
    private static int CACHE_ONLINE_DAYS = 2;

    private BiblesOrgAPI() {
    }

    @SuppressLint("AuthLeak")
    public static Retrofit getInstance(Context context) {
        if (httpClient == null) {
            httpClient = new OkHttpClient.Builder();
        }
        if (retrofit == null) {
            httpClient.addNetworkInterceptor(new AuthenticationInterceptor(Credentials.basic(apiKey, "x")))
                      .addNetworkInterceptor(new StethoInterceptor())
                      .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                      .addInterceptor(provideOfflineCacheInterceptor(context))
                      .addNetworkInterceptor(provideCacheInterceptor())
                      .cache(provideCache(context));

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    private static Cache provideCache (Context context) {
        Cache cache = null;
        try {
            cache = new Cache( new File( context.getCacheDir(), CACHE_FOLDER ), 10 * 1024 * 1024 ); // 10 MB
        }
        catch (Exception ignored) {}

        return cache;
    }

    private static class AuthenticationInterceptor implements Interceptor {
        private String authToken;

        AuthenticationInterceptor(String token) {
            this.authToken = token;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request original = chain.request();

            Request request = original
                    .newBuilder()
                    .removeHeader("Pragma")
                    .header("Authorization", authToken)
                    .build();

            return chain.proceed(request);
        }
    }

    private static Interceptor provideCacheInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());

            // re-write response header to force use of cache
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(CACHE_ONLINE_DAYS, TimeUnit.DAYS)
                    .build();

            return response.newBuilder()
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build();
        };
    }

    private static Interceptor provideOfflineCacheInterceptor(final Context context) {
        return chain -> {
            Request request = chain.request();

            if (!ConnectionUtils.isConnected(context)) {
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(CACHE_OFFLINE_DAYS, TimeUnit.DAYS)
                        .build();

                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }

            return chain.proceed(request);
        };
    }
}
