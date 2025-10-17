package com.example.absolutecinema.data

class SessionManager {
    var requestToken: String? = null
    var sessionId: String? = null

    fun clearSession() {
        requestToken = null
        sessionId = null
    }
}