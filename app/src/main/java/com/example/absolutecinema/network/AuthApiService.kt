package com.example.absolutecinema.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface AuthApiService {

    @GET("authentication/token/new")
    suspend fun getRequestToken(): Response<ResponseBody>
}