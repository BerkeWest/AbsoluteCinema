package com.example.absolutecinema.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResultPages(
    val page: Int,
    val results: List<MovieSearchResult>,
    val total_pages: Int,
    val total_results: Int
)

@Serializable
data class MovieSearchResult(
    val genre_ids: List<Int>,
    val id: Int = 0,
    val original_title: String,
    val overview: String,
    val popularity: Double=0.0,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val vote_average: Double = 0.0,
    val genre: String = ""
)

@Serializable
data class GenreList(
    val genres: List<Genre>
)

@Serializable
data class Genre(
    val id: Int = 0,
    val name: String
)