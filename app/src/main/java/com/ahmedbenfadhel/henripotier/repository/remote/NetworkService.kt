package com.ahmedbenfadhel.henripotier.repository.remote

import android.content.Context
import com.ahmedbenfadhel.henripotier.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class NetworkService(private val context: Context) {
    private val TIMEOUT_IN_SECONDS: Long = 30L
    private val cacheSize = 10 * 1024 * 1024 // 10 MB

    val service: HenriPotierService by lazy {
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        // OkHttp client builder
        val httpClient = OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)

        val gsonBuilder = GsonBuilder().create()

        // Retrofit builder for JSON calls
        val service = Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))

        if (BuildConfig.DEBUG) {
            // Http client interceptor
            val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(loggingInterceptor)
        }

        service.client(httpClient.build())

        service.build().create(HenriPotierService::class.java)
    }

}