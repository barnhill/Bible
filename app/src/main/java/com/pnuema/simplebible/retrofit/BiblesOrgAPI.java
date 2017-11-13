package com.pnuema.simplebible.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Houses all the api calls to get data
 */
public final class BiblesOrgAPI {
    @SuppressWarnings("FieldCanBeLocal") private static String baseUrl = "https://bibles.org/v2/";
    @SuppressWarnings("FieldCanBeLocal") private static String apiKey = "joMYGKFIjcYh1KiwYpE3lg08fla0cqEeSaM09II1";
    private static Retrofit retrofit;
    private static OkHttpClient.Builder httpClient;

    private BiblesOrgAPI() {
    }

    @SuppressLint("AuthLeak")
    public static Retrofit getInstance(Context context) {
        if (httpClient == null) {
            int cacheSize = 50 * 1024 * 1024; // 50 MB
            Cache cache = new Cache(context.getCacheDir(), cacheSize);
            httpClient = new OkHttpClient.Builder().cache(cache);
        }
        if (retrofit == null) {
            AuthenticationInterceptor authInterceptor = new AuthenticationInterceptor(Credentials.basic(apiKey, "x"));
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.addNetworkInterceptor(authInterceptor).addNetworkInterceptor(new StethoInterceptor()).build())
                    .build();
        }
        return retrofit;
    }

    private static class AuthenticationInterceptor implements Interceptor {
        private String authToken;

        AuthenticationInterceptor(String token) {
            this.authToken = token;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request original = chain.request();

            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(365, TimeUnit.DAYS)
                    .build();

            Request request = original
                    .newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Authorization", authToken)
                    .header("Cache-Control", cacheControl.toString())
                    .build();
            return chain.proceed(request);
        }
    }
}
