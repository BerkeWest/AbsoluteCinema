package com.example.absolutecinema.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class WatchListBody(
    val media_type: String = "movie",
    val media_id: Int,
    val watchlist: Boolean
)