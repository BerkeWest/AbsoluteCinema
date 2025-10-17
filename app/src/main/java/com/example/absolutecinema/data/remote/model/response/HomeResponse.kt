package com.example.absolutecinema.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class HomeResponse(
    val page: Int,
    val results: List<SearchResponse>,
    val total_pages: Int,
    val total_results: Int
)

@Serializable
data class HomeResponseDate(
    val dates: Dates,
    val page: Int,
    val results: List<SearchResponse>,
    val total_pages: Int,
    val total_results: Int
    )

@Serializable
data class Dates(
    val maximum: String,
    val minimum: String
)