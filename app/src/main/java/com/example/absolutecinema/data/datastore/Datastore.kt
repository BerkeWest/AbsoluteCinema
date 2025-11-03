package com.example.absolutecinema.data.datastore

import android.content.Context
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.prefDataStore: DataStore<Preferences> by preferencesDataStore(name = "session_id_secure")

val KEY = stringPreferencesKey("dataToEncrypt")

fun decodeString(str: String): ByteArray = Base64.decode(str, Base64.DEFAULT)
fun encodeArray(bytes: ByteArray): String = Base64.encodeToString(bytes, Base64.DEFAULT)