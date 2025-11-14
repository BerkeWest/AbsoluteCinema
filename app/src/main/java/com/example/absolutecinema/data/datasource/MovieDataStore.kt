package com.example.absolutecinema.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.absolutecinema.data.datastore.CryptoData
import com.example.absolutecinema.data.datastore.decodeString
import com.example.absolutecinema.data.datastore.encodeArray
import com.example.absolutecinema.data.datasource.local.MovieLocalDataSource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDataStore @Inject constructor(
    private val crypto: CryptoData,
    private val dataStore: DataStore<Preferences>
){

    var requestToken: String? = null

    private object PreferencesKeys {
        val ENCRYPTED_SESSION_ID = stringPreferencesKey("encrypted_session_id")
        val ENCRYPTED_ACCOUNT_ID = stringPreferencesKey("encrypted_account_id")
    }

    suspend fun getSessionId(): String? {
        val encryptedSessionId = dataStore.data.map { preferences ->
            preferences[PreferencesKeys.ENCRYPTED_SESSION_ID]
        }.firstOrNull() ?: return null

        return try {
            val decryptedBytes = crypto.decrypt(decodeString(encryptedSessionId))
            decryptedBytes?.toString(Charsets.UTF_8)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getAccountId(): Int? {
        val encryptedAccountId = dataStore.data.map { preferences ->
            preferences[PreferencesKeys.ENCRYPTED_ACCOUNT_ID]
        }.firstOrNull() ?: return null

        return try {
            val decryptedBytes = crypto.decrypt(decodeString(encryptedAccountId))
            decryptedBytes?.toString(Charsets.UTF_8)?.toIntOrNull()
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
    }

    suspend fun saveAccountId(accountId: Int) {
        val encryptedBytes = crypto.encrypt(accountId.toString().toByteArray(Charsets.UTF_8))
        val encryptedString = encodeArray(encryptedBytes)
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ENCRYPTED_ACCOUNT_ID] = encryptedString
        }
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.ENCRYPTED_SESSION_ID)
            preferences.remove(PreferencesKeys.ENCRYPTED_ACCOUNT_ID)
        }
        requestToken = null
    }
}