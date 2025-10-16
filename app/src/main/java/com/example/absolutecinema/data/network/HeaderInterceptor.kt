package com.example.absolutecinema.data.network

import com.example.absolutecinema.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
            .newBuilder()
            .header("Authorization", "Bearer ${BuildConfig.API_KEY}")
            .build()
        return chain.proceed(request)
    }
}