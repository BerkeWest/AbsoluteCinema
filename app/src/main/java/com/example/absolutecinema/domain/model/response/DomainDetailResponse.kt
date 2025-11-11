package com.example.absolutecinema.data.model.response

import com.example.absolutecinema.base.BaseDomainModel
import com.example.absolutecinema.domain.model.response.GenreDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDomainModel(
    val backdropPath: String?,
    val genres: List<GenreDomainModel>,
    val id: Int=0,
    val originalTitle: String,
    val overview: String,
    val popularity: Double=0.0,
    val posterPath: String,
    val releaseDate: String,
    val runtime: Int,
    val status: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
): BaseDomainModel

@Serializable
data class MovieStateDomainModel(
    val id: Int,
    val favorite: Boolean,
    val rated: Boolean,
    val watchlist: Boolean
): BaseDomainModel