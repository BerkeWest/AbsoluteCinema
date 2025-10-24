package com.example.absolutecinema.data.authentication

import com.example.absolutecinema.data.SessionManager
import com.example.absolutecinema.data.remote.model.request.LoginBody
import com.example.absolutecinema.data.remote.model.request.TokenBody


class AuthRepository(
    private val api: AuthApiService,
    private val sessionManager: SessionManager
){

    /*
    Request Token için api isteği atar. Eğer başarılı ise sessionManager'daki requestToken'ı günceller.
    */
    suspend fun fetchRequestToken(): String? {
        val response = api.getRequestToken()
        if (response.success) {
            sessionManager.requestToken = response.request_token
        }
        return sessionManager.requestToken
    }

    /*
    fethRequestToken methodunu çalıştırır ve dönen tokeni tutar.
    Login işlemi için api isteği atar. Eğer başarılı ise sessionId için istek atar ve sessionManagerda günceler.
    Artından accountId'yi çeker.
    Bütün bu işlemler sırasında dönen herhangi bir olumsuz durumda false döndürür.
    */
    suspend fun login(username: String, password: String): Boolean {
        val token = sessionManager.requestToken ?: fetchRequestToken() ?: return false
        val loginResponse = api.login(loginBody = LoginBody(username, password, token))
        if (loginResponse.success) {
            sessionManager.sessionId = createSession(token)
            getAccountId()
            if (sessionManager.sessionId != null) return true
            else return false
        }
        else return false
    }

    /*
    Session oluşturmak için api isteği atar. Eğer başarılı ise dönen sessionId'yi döndürür.
    */
    suspend fun createSession(requestToken: String): String? {
        val sessionResponse = api.createSession(TokenBody(requestToken))
        if (sessionResponse.success) return sessionResponse.session_id
        else return null
    }

    /*
    Account id'yi çekmek için api isteği atar.
    */
    suspend fun getAccountId(){
        val response = api.getAccountId()
        sessionManager.accountId = response.id
    }
}