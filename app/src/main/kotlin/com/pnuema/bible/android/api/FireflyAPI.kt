package com.pnuema.bible.android.api

import com.pnuema.bible.android.BuildConfig
import com.pnuema.bible.android.statics.App
import com.pnuema.bible.android.statics.ConnectionUtils.isConnected
import okhttp3.*
import okhttp3.brotli.BrotliInterceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Houses all the api calls to get data
 */
object FireflyAPI {
    private const val baseUrl = "https://fireflyapi.link/api/v1/"
    private const val CACHE_FOLDER = "http-cache"
    private const val HEADER_CACHE_CONTROL = "Cache-Control"
    private const val CACHE_OFFLINE_DAYS = 365
    private const val CACHE_ONLINE_DAYS = 2

    private val httpClient: OkHttpClient.Builder by lazy { OkHttpClient.Builder() }

    private val instance: Retrofit by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        httpClient
            .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(provideOfflineCacheInterceptor())
            .addNetworkInterceptor(provideCacheInterceptor())
            .addInterceptor(BrotliInterceptor)
            .cache(provideCache())

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    val api: IFireflyAPI by lazy { instance.create(IFireflyAPI::class.java) }

    private fun provideCache(): Cache? {
        try {
            return Cache(File(App.context.cacheDir, CACHE_FOLDER), 10 * 1024 * 1024L) // 10 MB
        } catch (ignored: Exception) {
        }
        return null
    }

    private fun provideCacheInterceptor(): Interceptor = Interceptor { chain: Interceptor.Chain ->
        val response = chain.proceed(chain.request())

        // re-write response header to force use of cache
        val cacheControl: CacheControl = CacheControl.Builder()
                .maxAge(CACHE_ONLINE_DAYS, TimeUnit.DAYS)
                .build()
        response.newBuilder()
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
    }

    private fun provideOfflineCacheInterceptor(): Interceptor = Interceptor { chain: Interceptor.Chain ->
        var request = chain.request()
        if (!isConnected(App.context)) {
            val cacheControl: CacheControl = CacheControl.Builder()
                    .maxStale(CACHE_OFFLINE_DAYS, TimeUnit.DAYS)
                    .build()
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
        }
        chain.proceed(request)
    }
}