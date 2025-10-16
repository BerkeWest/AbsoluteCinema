package com.example.absolutecinema.data

import com.example.absolutecinema.BuildConfig
import com.example.absolutecinema.data.network.AuthApiService
import com.example.absolutecinema.data.network.HeaderInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


interface AppContainer {
    val authRepository: AppRepository
}

class DefaultAppContainer : AppContainer {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    private val apiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    override val authRepository: AppRepository by lazy {
        NetworkAppRepository(apiService)
    }
}