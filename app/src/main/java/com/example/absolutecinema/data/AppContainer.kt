package com.example.absolutecinema.data

import com.example.absolutecinema.BuildConfig
import com.example.absolutecinema.data.authentication.AuthApiService
import com.example.absolutecinema.data.authentication.AuthRepository
import com.example.absolutecinema.data.movie.MovieApiService
import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.data.network.HeaderInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class AppContainer {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    private val retrofitImage: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BuildConfig.IMAGE_URL)
        .build()

    val sessionManager = SessionManager()

    //Authentication
    private val authApiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }
    val authRepository: AuthRepository by lazy {
        AuthRepository(authApiService, sessionManager)
    }

    //Movie(HomePage etc.)
    private val movieApiService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }

    val movieRepository: MovieRepository by lazy {
        MovieRepository(movieApiService, sessionManager)
    }
}