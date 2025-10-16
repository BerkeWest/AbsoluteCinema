package com.example.absolutecinema.data

import com.example.absolutecinema.data.network.AuthApiService
import com.example.absolutecinema.data.remote.model.request.LoginBody
import com.example.absolutecinema.data.remote.model.request.TokenBody
import com.example.absolutecinema.data.remote.model.response.SessionResponse

interface AppRepository {
    suspend fun login(username: String, password: String): Boolean
    suspend fun fetchRequestToken(): String?

    suspend fun createSession(requestToken: String): String?
}

class NetworkAppRepository(private val api: AuthApiService) : AppRepository {
    private var requestToken: String? = null
    private var sessionId: String? = null


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
        if (response.success) {
            sessionId = createSession(token)
            if (sessionId != null) return response.success
            else return false
        }
        else return false
    }

    override suspend fun createSession(requestToken: String): String? {
        val response2 = api.createSession(TokenBody(requestToken))
        if (response2.success) return response2.session_id
        else return null
    }
}