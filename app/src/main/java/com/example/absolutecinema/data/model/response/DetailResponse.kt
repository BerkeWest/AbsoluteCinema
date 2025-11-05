package com.example.absolutecinema.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    val backdrop_path: String?,
    val genres: List<Genre>,
    val id: Int=0,
    val original_title: String,
    val overview: String,
    val popularity: Double=0.0,
    val poster_path: String,
    val release_date: String,
    val runtime: Int,
    val status: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
)

@Serializable
data class MovieState(
    val id: Int,
    val favorite: Boolean,
    val rated: Boolean,
    val watchlist: Boolean
)