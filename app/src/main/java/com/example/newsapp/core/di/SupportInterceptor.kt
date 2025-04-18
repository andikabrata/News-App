package com.example.newsapp.core.di

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
class SupportInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("Content-Type", "application/json")
            .removeHeader("Pragma")
            .header("Cache-Control", String.format("max-age=%d", 432000))
            .build()
        return chain.proceed(request)
    }
}