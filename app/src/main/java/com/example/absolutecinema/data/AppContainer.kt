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
/*
Interceptorları içeren client.
 */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    /*
      Retrofitin kullandığı Json'ın yapılandırmasını yapar.
      Api data classta olmayan bir field gönderirse görmezden gelir.
      Api null veri gönderirse data class defaultu kullanır.
     */
    val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    /*
    Api service interfaceleri çalışır koda çevirir.
    Convertor factory, JSON datasını nasıl Kotlin objesine çevireceğini belirtir.
    Yaptığımız özel clientı kullanır.
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json
            .asConverterFactory("application/json".toMediaType()))
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    /*
    Session manager oluşturur.
     */
    val sessionManager = SessionManager()


    //Authentication
    /*
    by lazy ile ilk kez çalıştırılacağı zaman api service oluşturulur.
     */
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