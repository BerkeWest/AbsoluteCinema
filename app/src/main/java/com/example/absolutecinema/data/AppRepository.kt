package com.example.absolutecinema.data

import com.example.absolutecinema.data.network.AuthApiService
import com.example.absolutecinema.data.remote.model.request.LoginBody

interface AppRepository {
    suspend fun login(username: String, password: String): Boolean
    suspend fun fetchRequestToken(): String?
}

class NetworkAppRepository(private val api: AuthApiService) : AppRepository {
    private var requestToken: String? = null

    override suspend fun fetchRequestToken(): String? {
        val response = api.getRequestToken()
        if (response.success) {
            requestToken = response.request_token
        }
        return requestToken
    }

    override suspend fun login(username: String, password: String): Boolean {
        val token = requestToken ?: fetchRequestToken() ?: return false
        val response = api.login(loginBody = LoginBody(username, password, token))
        return response.success
    }


}