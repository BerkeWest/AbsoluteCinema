package com.example.absolutecinema.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SearchItem(
    val genre_ids: List<Int>,
    val id: Int = 0,
    val original_title: String,
    val overview: String,
    val popularity: Double=0.0,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val vote_average: Double = 0.0,
)