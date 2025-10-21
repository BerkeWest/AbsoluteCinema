package com.example.absolutecinema.data.remote.model.response

import com.example.absolutecinema.data.remote.model.request.SearchItem
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val page: Int,
    val results: List<SearchItem>,
    val total_pages: Int,
    val total_results: Int
)

@Serializable
data class MovieDetails(
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
data class Genre(
    val id: Int = 0,
    val name: String
)