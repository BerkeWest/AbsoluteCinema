package com.example.absolutecinema.data.authentication

import com.example.absolutecinema.data.model.request.Account
import com.example.absolutecinema.data.model.request.LoginBody
import com.example.absolutecinema.data.model.request.TokenBody
import com.example.absolutecinema.data.model.response.RequestTokenResponse
import com.example.absolutecinema.data.model.response.SessionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AuthApiService {

    @GET("authentication/token/new")
    suspend fun getRequestToken(): RequestTokenResponse

    @POST("authentication/token/validate_with_login")
    suspend fun login(
        @Body loginBody: LoginBody
    ): RequestTokenResponse

    @POST("authentication/session/new")
    suspend fun createSession(@Body tokenBody: TokenBody): SessionResponse

    @GET("account")
    suspend fun getAccountId(): Account



}