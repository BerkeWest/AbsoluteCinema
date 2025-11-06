package com.example.absolutecinema.data.authentication

import com.example.absolutecinema.data.SessionManager
import com.example.absolutecinema.data.model.request.LoginBody
import com.example.absolutecinema.data.model.request.TokenBody
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: AuthApiService,
    private val sessionManager: SessionManager
) {

    /*
    Request Token için api isteği atar. Eğer başarılı ise sessionManager'daki requestToken'ı günceller.
    */
    suspend fun fetchRequestToken(): String{
        val response = api.getRequestToken()
        if (response.success) {
            sessionManager.requestToken = response.request_token
        }
        return sessionManager.requestToken
    }

    suspend fun hasAccess(): Boolean {
        val sessionId = sessionManager.getSessionId()
        if (sessionId != null) {
            getAccountId()
            return true
        }
        return false
    }


    /*
    fethRequestToken methodunu çalıştırır ve dönen tokeni tutar.
    Login işlemi için api isteği atar. Eğer başarılı ise sessionId için istek atar ve sessionManagerda günceler.
    Artından accountId'yi çeker.
    Bütün bu işlemler sırasında dönen herhangi bir olumsuz durumda false döndürür.
    */
    suspend fun login(username: String, password: String): Boolean {
        val token = fetchRequestToken()
        val loginResponse = api.login(loginBody = LoginBody(username, password, token))
        if (loginResponse.success) {
            val newSessionId = createSession(token)
            if (newSessionId != null) {
                sessionManager.saveSessionId(newSessionId) // Yeni save fonksiyonu
                getAccountId()
                return true
            }
        }
        return false
    }

    /*
    Session oluşturmak için api isteği atar. Eğer başarılı ise dönen sessionId'yi döndürür.
    */
    suspend fun createSession(requestToken: String): String? {
        val sessionResponse = api.createSession(TokenBody(requestToken))
        return if (sessionResponse.success) sessionResponse.session_id else null
    }

    suspend fun logout() {
        sessionManager.clearSession()
    }


    /*
    Account id'yi çekmek için api isteği atar.
    */
    suspend fun getAccountId() {
        val response = api.getAccountId()
        sessionManager.accountId = response.id
    }
}