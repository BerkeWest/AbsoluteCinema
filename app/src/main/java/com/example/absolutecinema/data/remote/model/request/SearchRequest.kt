package com.example.absolutecinema.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SearchItem(
    val id: Int,
    val title: String,
    val popularity: Double
)