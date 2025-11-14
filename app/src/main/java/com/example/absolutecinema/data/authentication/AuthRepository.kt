package com.example.absolutecinema.data.authentication

import com.example.absolutecinema.data.datasource.local.MovieLocalDataSourceImpl
import com.example.absolutecinema.data.datasource.remote.MovieRemoteDataSourceImpl
import com.example.absolutecinema.data.model.request.LoginBody
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.flow

@Singleton
class AuthRepository @Inject constructor(
    private val api: AuthApiService,
    private val localDataSource: MovieLocalDataSourceImpl,
    private val remoteDataSource: MovieRemoteDataSourceImpl
) {

    /*
    Request Token için api isteği atar. Eğer başarılı ise sessionManager'daki requestToken'ı günceller.
    */
    suspend fun fetchRequestToken(): String? {
        return if (localDataSource.getLocalRequestToken() != null) localDataSource.getLocalRequestToken()
        else remoteDataSource.getRemoteRequestToken()
    }

    fun hasAccess() = flow {
        val sessionId = localDataSource.getLocalSessionId()
        if (sessionId != null) {
            emit(true)
        } else emit(false)
    }


    /*
    fethRequestToken methodunu çalıştırır ve dönen tokeni tutar.
    Login işlemi için api isteği atar. Eğer başarılı ise sessionId için istek atar ve sessionManagerda günceler.
    Artından accountId'yi çeker.
    Bütün bu işlemler sırasında dönen herhangi bir olumsuz durumda false döndürür.
    */
    suspend fun login(username: String, password: String): Boolean {
        val token = fetchRequestToken()
        if (token == null) return false
        val loginResponse = api.login(loginBody = LoginBody(username, password, token))
        if (loginResponse.success) {
            val newSessionId = createSession(loginResponse.requestToken)
            if (newSessionId != null) {
                getAccountId(newSessionId)
                return true
            }
        }
        return false
    }

    /*
    Session oluşturmak için api isteği atar. Eğer başarılı ise dönen sessionId'yi döndürür.
    */
    suspend fun createSession(requestToken: String): String? {
        return if (localDataSource.getLocalSessionId() != null) localDataSource.getLocalSessionId()
        else remoteDataSource.getRemoteSessionId(requestToken)
    }

    fun logout() = flow {
        localDataSource.clearLocalSession()
        emit(Unit)
    }


    /*
    Account id'yi çekmek için api isteği atar.
    */
    suspend fun getAccountId(sessionId: String): Int? {
        return if (localDataSource.getLocalAccountId() != null) localDataSource.getLocalAccountId()
        else remoteDataSource.getRemoteAccountId(sessionId)
    }
}