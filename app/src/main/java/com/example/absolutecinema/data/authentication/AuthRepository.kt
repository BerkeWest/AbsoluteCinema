package com.example.absolutecinema.data.authentication

import com.example.absolutecinema.data.SessionManager
import com.example.absolutecinema.data.remote.model.request.LoginBody
import com.example.absolutecinema.data.remote.model.request.TokenBody


class AuthRepository(
    private val api: AuthApiService,
    private val sessionManager: SessionManager
){

    suspend fun fetchRequestToken(): String? {
        val response = api.getRequestToken()
        if (response.success) {
            sessionManager.requestToken = response.request_token
        }
        return sessionManager.requestToken
    }

    suspend fun login(username: String, password: String): Boolean {
        val token = sessionManager.requestToken ?: fetchRequestToken() ?: return false
        val loginResponse = api.login(loginBody = LoginBody(username, password, token))
        if (loginResponse.success) {
            sessionManager.sessionId = createSession(token)
            getAccountId()
            if (sessionManager.sessionId != null) return loginResponse.success
            else return false
        }
        else return false
    }

    suspend fun createSession(requestToken: String): String? {
        val sessionResponse = api.createSession(TokenBody(requestToken))
        if (sessionResponse.success) return sessionResponse.session_id
        else return null
    }

    suspend fun getAccountId(){
        val response = api.getAccountId()
        sessionManager.accountId = response.id
    }
}