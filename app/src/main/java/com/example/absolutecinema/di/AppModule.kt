package com.example.absolutecinema.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.absolutecinema.BuildConfig
import com.example.absolutecinema.data.authentication.AuthApiService
import com.example.absolutecinema.data.authentication.AuthRepository
import com.example.absolutecinema.data.datastore.CryptoData
import com.example.absolutecinema.data.movie.MovieApiService
import com.example.absolutecinema.data.movie.MovieRepository
// Düzeltme: Doğru importları ekleyin
import com.example.absolutecinema.data.datasource.local.MovieLocalDataSource
import com.example.absolutecinema.data.datasource.local.MovieLocalDataSourceImpl
import com.example.absolutecinema.data.datasource.remote.MovieRemoteDataSource
import com.example.absolutecinema.data.datasource.remote.MovieRemoteDataSourceImpl
import com.example.absolutecinema.data.network.HeaderInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session_id_secure")

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
    fun provideAuthRepository(
        authApiService: AuthApiService,
        localDataSource: MovieLocalDataSourceImpl,
        remoteDataSource: MovieRemoteDataSourceImpl
    ): AuthRepository {
        return AuthRepository(authApiService, localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApiService: MovieApiService,
        localDataSource: MovieLocalDataSourceImpl
    ): MovieRepository {
        return MovieRepository(movieApiService, localDataSource)
    }

    @Provides
    @Singleton
    fun provideCryptoData(): CryptoData {
        return CryptoData()
    }

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}

// DÜZELTME 1: DataSourceModule'ü @Binds kullanacak şekilde düzeltin
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindMovieLocalDataSource(
        movieLocalDataSourceImpl: MovieLocalDataSourceImpl
    ): MovieLocalDataSource

    @Binds
    @Singleton
    abstract fun bindMovieRemoteDataSource(
        movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl
    ): MovieRemoteDataSource
}


@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher
