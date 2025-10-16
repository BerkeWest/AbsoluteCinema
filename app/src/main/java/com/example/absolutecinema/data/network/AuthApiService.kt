package com.example.absolutecinema.data.network

import com.example.absolutecinema.data.remote.model.request.LoginBody
import com.example.absolutecinema.data.remote.model.response.RequestTokenResponse
import com.example.absolutecinema.data.remote.model.response.SessionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

//Minify enabald @Serilaname
//
// minify enabled kullanılmayan class, method vs kaldırır,
// kullanılanları a,b,c gibi yeniden adlandırarak reverse engineeri zorlaştırır ama bu esnada @Serializable kullanan klassları da değiştireceğinden json okumalarını bozar.
// bunun için proguardda keep ataması yazılmalı veya o classlar @Keep ile tutulmalı
interface AuthApiService {

    @GET("authentication/token/new")
    suspend fun getRequestToken(): RequestTokenResponse

    @POST("authentication/token/validate_with_login")
    suspend fun login(
        @Body loginBody: LoginBody
    ): RequestTokenResponse

    @POST("authentication/session/new")
    suspend fun createSession(@Body requestToken: String): SessionResponse

}