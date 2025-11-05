package com.example.absolutecinema.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(
    val username: String,
    val password: String,
    val request_token: String,
)

@Serializable
data class TokenBody(
    val request_token: String
)

@Serializable
data class Account(
    val id: Int,
)
