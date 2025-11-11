package com.example.absolutecinema.data.authentication

import com.example.absolutecinema.data.model.request.Account
import com.example.absolutecinema.data.model.request.LoginBody
import com.example.absolutecinema.data.model.request.TokenBody
import com.example.absolutecinema.data.model.response.RequestTokenResponseRemoteDataModel
import com.example.absolutecinema.data.model.response.SessionResponseRemoteDataModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface AuthApiService {

    @GET("authentication/token/new")
    suspend fun getRequestToken(): RequestTokenResponseRemoteDataModel

    @POST("authentication/token/validate_with_login")
    suspend fun login(
        @Body loginBody: LoginBody
    ): RequestTokenResponseRemoteDataModel

    @POST("authentication/session/new")
    suspend fun createSession(@Body tokenBody: TokenBody): SessionResponseRemoteDataModel

    @GET("account")
    suspend fun getAccountId(@Query("session_id") sessionId: String?): Account


}