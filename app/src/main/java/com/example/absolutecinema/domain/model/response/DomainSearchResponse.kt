package com.example.absolutecinema.domain.model.response

import com.example.absolutecinema.domain.base.BaseDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class MovieSearchResultDomainModel(
    val genreIds: List<Int>?,
    val id: Int?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val voteAverage: Double?,
    val genre: String?
) : BaseDomainModel

@Serializable
data class GenreDomainModel(
    val id: Int = 0,
    val name: String
) : BaseDomainModel