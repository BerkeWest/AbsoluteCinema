package com.example.absolutecinema.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class RequestTokenResponse(
    val success: Boolean,
    val expires_at: String,
    val request_token: String
)

@Serializable
data class SessionResponse(
    val success: Boolean,
    val session_id: String
)