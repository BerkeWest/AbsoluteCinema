package com.example.absolutecinema.data.datasource.local


interface MovieLocalDataSource {

    suspend fun getLocalRequestToken(): String?

    suspend fun getLocalSessionId(): String?

    suspend fun getLocalAccountId(): Int?

    suspend fun saveLocalSessionId(sessionId: String)

    suspend fun saveLocalAccountId(accountId: Int)

    suspend fun clearLocalSession()

}