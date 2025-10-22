package com.example.absolutecinema.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MovieState(
    val id: Int,
    val favorite: Boolean,
    val rated: Boolean,
    val watchlist: Boolean
)