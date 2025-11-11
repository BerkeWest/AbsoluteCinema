package com.example.absolutecinema.data.model.response

import com.example.absolutecinema.base.BaseDataModel
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