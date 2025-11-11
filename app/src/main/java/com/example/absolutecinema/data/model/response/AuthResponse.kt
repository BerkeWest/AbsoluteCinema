package com.example.absolutecinema.data.model.response

import com.example.absolutecinema.base.BaseDataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestTokenResponseRemoteDataModel(
    @SerialName("success") val success: Boolean,
    @SerialName("expires_at") val expiresAt: String,
    @SerialName("request_token") val requestToken: String
) : BaseDataModel

@Serializable
data class SessionResponseRemoteDataModel(
    val success: Boolean,
    @SerialName("session_id") val sessionId: String
) : BaseDataModel