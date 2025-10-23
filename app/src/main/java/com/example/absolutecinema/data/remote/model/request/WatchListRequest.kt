package com.example.absolutecinema.data.remote.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchListBody(
    @SerialName("media_type") val mediaType: String,
    val media_id: Int?,
    val watchlist: Boolean
)