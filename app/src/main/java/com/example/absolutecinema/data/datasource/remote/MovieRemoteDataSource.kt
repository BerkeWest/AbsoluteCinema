package com.example.absolutecinema.data.datasource.remote


interface MovieRemoteDataSource {

    suspend fun getRemoteRequestToken(): String?

    suspend fun getRemoteSessionId(requestToken: String): String?

    suspend fun getRemoteAccountId(sessionId: String): Int?

}