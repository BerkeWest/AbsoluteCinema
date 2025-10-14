package com.example.absolutecinema.data

import com.example.absolutecinema.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


object AppContainer {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder().apply {
                addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
            }.build()
            chain.proceed(request)
        }
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    lateinit var REFRESH_TOKEN: String

    lateinit var USER_ID: String
}