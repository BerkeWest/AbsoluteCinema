package com.example.absolutecinema.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(
    val username: String,
    val password: String,
    val request_token: String,
)
