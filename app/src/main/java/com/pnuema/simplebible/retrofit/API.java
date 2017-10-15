package com.pnuema.simplebible.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.pnuema.simplebible.statics.ConnectionUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Cache;
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
public final class API {
    @SuppressWarnings("FieldCanBeLocal") private static String baseUrl = "https://bibles.org/v2/";
    @SuppressWarnings("FieldCanBeLocal") private static String apiKey = "joMYGKFIjcYh1KiwYpE3lg08fla0cqEeSaM09II1";
    private static Retrofit retrofit;
    private static OkHttpClient.Builder httpClient;

    @SuppressLint("AuthLeak")
    public static Retrofit getInstance(Context context) {
        if (httpClient == null) {
            int cacheSize = 50 * 1024 * 1024; // 50 MB
            Cache cache = new Cache(context.getCacheDir(), cacheSize);
            httpClient = new OkHttpClient.Builder().cache(cache);
        }
        if (retrofit == null) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(context, Credentials.basic(apiKey, "x"));
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.addInterceptor(interceptor).build())
                    .build();
        }
        return retrofit;
    }

    private static class AuthenticationInterceptor implements Interceptor {
        private String authToken;
        private WeakReference<Context> context;

        AuthenticationInterceptor(Context context, String token) {
            this.authToken = token;
            this.context = new WeakReference<>(context);
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original
                    .newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Authorization", authToken)
                    .header("Cache-Control", context.get() != null && ConnectionUtils.isConnected(context.get())
                            ? "public, max-age=2419200" //4 weeks cache when connected
                            : "public, only-if-cached, max-stale=31536000") //1 year cache when not connected
                    .build();
            return chain.proceed(request);
        }
    }
}
