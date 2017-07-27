package com.pnuema.simplebible.retrofit;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.io.IOException;

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
    private static Retrofit retrofit;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    @SuppressLint("AuthLeak")
    public static Retrofit getInstance() {
        if (retrofit == null) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(Credentials.basic("joMYGKFIjcYh1KiwYpE3lg08fla0cqEeSaM09II1", "x"));
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://bibles.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.addInterceptor(interceptor).build())
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
            Request request = original.newBuilder().header("Authorization", authToken).build();
            return chain.proceed(request);
        }
    }
}
