package com.example.absolutecinema.domain.mapper

import com.example.absolutecinema.domain.base.BaseDomainMapper
import com.example.absolutecinema.data.model.response.MovieSearchResultRemoteDataModel
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel

object MovieSearchResultDomainMapper :
    BaseDomainMapper<MovieSearchResultRemoteDataModel, MovieSearchResultDomainModel> {
    override fun MovieSearchResultRemoteDataModel.toDomain() = MovieSearchResultDomainModel(
        genreIds = genreIds,
        id = id,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        genre = null
    )

    override fun MovieSearchResultDomainModel.toData(): MovieSearchResultRemoteDataModel {
        throw Exception("Not implemented")
    }
}