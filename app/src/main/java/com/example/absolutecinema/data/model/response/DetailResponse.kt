package com.example.absolutecinema.data.model.response

import com.example.absolutecinema.domain.base.BaseDataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRemoteDataModel(
    @SerialName("backdrop_path") val backdropPath: String?,
    val genres: List<GenreRemoteDataModel>,
    val id: Int = 0,
    @SerialName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double = 0.0,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("release_date") val releaseDate: String,
    val runtime: Int,
    val status: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average") val voteAverage: Double,
) : BaseDataModel

@Serializable
data class MovieStateRemoteDataModel(
    val id: Int,
    val favorite: Boolean,
    val rated: Boolean,
    val watchlist: Boolean
) : BaseDataModel

@Serializable
data class MovieCastRemoteDataModel(
    val cast: List<CastRemoteDataModel>,
): BaseDataModel

@Serializable
data class CastRemoteDataModel(
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val profilePath: String?,
    @SerialName("character")
    val character: String,
): BaseDataModel

@Serializable
data class ReviewsRemoteDataModel(
    @SerialName("results")
    val results: List<ReviewResultRemoteDataModel>
): BaseDataModel

@Serializable
data class ReviewResultRemoteDataModel(
    @SerialName("author")
    val author: String,
    @SerialName("author_details") val authorDetails: AuthorRemoteDataModel,
    val content: String,
): BaseDataModel

@Serializable
data class AuthorRemoteDataModel(
    @SerialName("name")
    val name: String,
    @SerialName("username")
    val username: String,
    @SerialName("avatar_path")
    val avatarPath: String?,
    @SerialName("rating")
    val rating: Double?
): BaseDataModel