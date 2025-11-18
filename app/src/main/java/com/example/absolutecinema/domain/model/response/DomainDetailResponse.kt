package com.example.absolutecinema.data.model.response

import com.example.absolutecinema.base.BaseDomainModel
import com.example.absolutecinema.domain.model.response.GenreDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDomainModel(
    val backdropPath: String?,
    val genres: List<GenreDomainModel>,
    val id: Int = 0,
    val originalTitle: String,
    val overview: String,
    val popularity: Double = 0.0,
    val posterPath: String,
    val releaseDate: String,
    val runtime: Int,
    val status: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
) : BaseDomainModel

@Serializable
data class MovieStateDomainModel(
    val id: Int,
    val favorite: Boolean,
    val rated: Boolean,
    val watchlist: Boolean
) : BaseDomainModel

@Serializable
data class CastDomainModel(
    val name: String,
    val profilePath: String?,
    val character: String,
) : BaseDomainModel


@Serializable
data class ReviewsDomainModel(
    val results: List<ReviewResultDomainModel>
) : BaseDomainModel

@Serializable
data class ReviewResultDomainModel(
    val author: String,
    val authorDetails: AuthorDomainModel,
    val content: String,
) : BaseDomainModel

@Serializable
data class AuthorDomainModel(
    val name: String,
    val username: String,
    val avatarPath: String?,
    val rating: Double?
) : BaseDomainModel