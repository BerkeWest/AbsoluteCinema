package com.example.absolutecinema.data.model.response

import com.example.absolutecinema.base.BaseDataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultPagesRemoteDataModel(
    val page: Int,
    val results: List<MovieSearchResultRemoteDataModel>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
) : BaseDataModel

@Serializable
data class MovieSearchResultRemoteDataModel(
    @SerialName("genre_ids") val genreIds: List<Int>?,
    val id: Int?,
    @SerialName("original_title") val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String?,
    val title: String?,
    @SerialName("vote_average") val voteAverage: Double?,
) : BaseDataModel

@Serializable
data class GenreListRemoteDataModel(
    val genres: List<GenreRemoteDataModel>
) : BaseDataModel

@Serializable
data class GenreRemoteDataModel(
    val id: Int = 0,
    val name: String
) : BaseDataModel