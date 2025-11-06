package com.example.absolutecinema.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.absolutecinema.data.datastore.CryptoData
import com.example.absolutecinema.data.datastore.decodeString
import com.example.absolutecinema.data.datastore.encodeArray
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val crypto: CryptoData,
    private val dataStore: DataStore<Preferences>
) {
    var accountId: Int? = null
    var requestToken: String = ""
    private var _sessionId: String? = null

    private object PreferencesKeys {
        val ENCRYPTED_SESSION_ID = stringPreferencesKey("encrypted_session_id")
    }

    suspend fun getSessionId(): String? {
        if (_sessionId != null) return _sessionId

        val encryptedSessionId = dataStore.data.map { preferences ->
            preferences[PreferencesKeys.ENCRYPTED_SESSION_ID]
        }.firstOrNull() ?: return null

        return try {
            val decryptedBytes = crypto.decrypt(decodeString(encryptedSessionId))
            val sessionId = decryptedBytes?.toString(Charsets.UTF_8)
            _sessionId = sessionId
            sessionId
        } catch (e: Exception) {
            null
        }
    }

    suspend fun saveSessionId(sessionId: String) {
        val encryptedBytes = crypto.encrypt(sessionId.toByteArray(Charsets.UTF_8))
        val encryptedString = encodeArray(encryptedBytes)
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ENCRYPTED_SESSION_ID] = encryptedString
        }
        _sessionId = sessionId
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.ENCRYPTED_SESSION_ID)
        }
        _sessionId = null
        accountId = null
    }
}