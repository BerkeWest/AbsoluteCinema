package com.example.absolutecinema.di

import android.content.Context
import com.example.absolutecinema.BuildConfig
import com.example.absolutecinema.data.SessionManager
import com.example.absolutecinema.data.authentication.AuthApiService
import com.example.absolutecinema.data.authentication.AuthRepository
import com.example.absolutecinema.data.movie.MovieApiService
import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.data.network.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }

    // --- ApiServices ---
    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }

    // --- Repositories ---
    @Provides
    @Singleton
    fun provideAuthRepository(authApiService: AuthApiService, sessionManager: SessionManager): AuthRepository {
        return AuthRepository(authApiService, sessionManager)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieApiService: MovieApiService, sessionManager: SessionManager): MovieRepository {
        return MovieRepository(movieApiService, sessionManager)
    }
}
