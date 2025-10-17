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