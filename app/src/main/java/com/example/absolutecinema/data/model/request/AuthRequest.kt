package com.example.absolutecinema.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(
    @SerialName("username") val username: String,
    @SerialName("password") val password: String,
    @SerialName("request_token") val requestToken: String,
)

@Serializable
data class TokenBody(
    @SerialName("request_token") val requestToken: String
)

@Serializable
data class Account(
    @SerialName("id") val id: Int,
)
