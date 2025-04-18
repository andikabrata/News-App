package com.example.newsapp.core.di

import android.content.Context
import com.example.newsapp.BuildConfig
import com.example.newsapp.core.api.ServiceApi
import okhttp3.Cache
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */

private const val CACHE_FILE_SIZE: Long = 30 * 1000 * 1000 // 30 Mib

private var baseUrl = "https://api.spaceflightnewsapi.net/v4/"
const val IMAGE_SOURCE = "https://image.tmdb.org/t/p/w500/"
private const val TIMEOUT: Long = 60

val retrofitModule = module {
    single<Call.Factory> {
        val cacheFile = cacheFile(androidContext())
        val cache = cache(cacheFile)
        okhttp(cache)
    }

    single {
        retrofit(get(), baseUrl)
    }

    single {
        get<Retrofit>().create(ServiceApi::class.java)
    }
}

private fun cacheFile(context: Context) = File(context.filesDir, "my_own_created_cache").apply {
    if (!this.exists())
        mkdirs()
}

private fun cache(cacheFile: File) = Cache(
    cacheFile,
    CACHE_FILE_SIZE
)

private fun retrofit(callFactory: Call.Factory, baseUrl: String) = Retrofit.Builder()
    .callFactory(callFactory)
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

private fun okhttp(cache: Cache) = OkHttpClient.Builder()
    .addInterceptor(SupportInterceptor())
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    })
    .cache(cache)
    .readTimeout(60, TimeUnit.SECONDS)
    .connectTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .build()
