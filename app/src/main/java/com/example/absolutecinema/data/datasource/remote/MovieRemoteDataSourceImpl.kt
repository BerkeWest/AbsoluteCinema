package com.example.absolutecinema.data.datasource.remote

import com.example.absolutecinema.data.authentication.AuthApiService
import com.example.absolutecinema.data.datasource.MovieDataStore
import com.example.absolutecinema.data.model.request.TokenBody
import jakarta.inject.Inject


class MovieRemoteDataSourceImpl @Inject constructor(
    private val dataStore: MovieDataStore,
    private val api: AuthApiService,
) : MovieRemoteDataSource {

    override suspend fun getRemoteRequestToken(): String? {
        val response = api.getRequestToken()
        if (response.success) {
            dataStore.requestToken = response.requestToken
        }
        return response.requestToken
    }

    override suspend fun getRemoteSessionId(requestToken: String): String? {
        val sessionResponse = api.createSession(TokenBody(requestToken))
        if (sessionResponse.success) {
            dataStore.saveSessionId(sessionResponse.sessionId)
            return sessionResponse.sessionId
        }
        return null
    }

    override suspend fun getRemoteAccountId(sessionId: String): Int? {
        try {
            val response = api.getAccountId(sessionId)
            dataStore.saveAccountId(response.id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dataStore.getAccountId()
    }

}