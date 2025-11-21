package com.example.absolutecinema.data.datasource.local

import com.example.absolutecinema.data.datasource.MovieDataStore
import javax.inject.Inject


class MovieLocalDataSourceImpl @Inject constructor(
    private val dataStore: MovieDataStore
): MovieLocalDataSource {

    override suspend fun getLocalRequestToken(): String? {
        return dataStore.requestToken
    }

    override suspend fun getLocalSessionId(): String? {
        return dataStore.getSessionId()
    }

    override suspend fun getLocalAccountId(): Int? {
        return dataStore.getAccountId()
    }

    override suspend fun saveLocalSessionId(sessionId: String) {
        dataStore.saveSessionId(sessionId)
    }

    override suspend fun saveLocalAccountId(accountId: Int) {
        dataStore.saveAccountId(accountId)
    }

    override suspend fun clearLocalSession() {
        dataStore.clearSession()
    }
}