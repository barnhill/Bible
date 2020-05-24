package com.pnuema.bible.android.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.pnuema.bible.android.statics.ConnectionUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import okhttp3.Cache;
import okhttp3.CacheControl;
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
public final class FireflyAPI {
    @SuppressWarnings("FieldCanBeLocal") private static String baseUrl = "http://firefly.pnuema.com/api/v1/";
    private static Retrofit retrofit;
    private static OkHttpClient.Builder httpClient;
    private static String CACHE_FOLDER = "http-cache";
    private static String HEADER_CACHE_CONTROL = "Cache-Control";
    private static int CACHE_OFFLINE_DAYS = 365;
    private static int CACHE_ONLINE_DAYS = 2;

    private FireflyAPI() {
    }

    @SuppressLint("AuthLeak")
    public static Retrofit getInstance(final Context context) {
        if (null == httpClient) {
            httpClient = new OkHttpClient.Builder();
        }
        if (null == retrofit) {
            final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            httpClient.callTimeout(60, TimeUnit.SECONDS)
                      .connectTimeout(20, TimeUnit.SECONDS)
                      .readTimeout(30, TimeUnit.SECONDS)
                      .writeTimeout(30, TimeUnit.SECONDS)
                      .addNetworkInterceptor(new StethoInterceptor())
                      .addInterceptor(httpLoggingInterceptor)
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

    @Nullable
    private static Cache provideCache (final Context context) {
        try {
            return new Cache( new File( context.getCacheDir(), CACHE_FOLDER ), 10 * 1024 * 1024 ); // 10 MB
        }
        catch (final Exception ignored) {}

        return null;
    }

    private static Interceptor provideCacheInterceptor() {
        return chain -> {
            final Response response = chain.proceed(chain.request());

            // re-write response header to force use of cache
            final CacheControl cacheControl = new CacheControl.Builder()
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

            if (!ConnectionUtils.INSTANCE.isConnected(context)) {
                final CacheControl cacheControl = new CacheControl.Builder()
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
