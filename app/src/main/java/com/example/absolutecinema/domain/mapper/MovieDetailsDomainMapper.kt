package com.example.absolutecinema.domain.mapper

import com.example.absolutecinema.domain.base.BaseDomainMapper
import com.example.absolutecinema.data.model.response.MovieDetailsDomainModel
import com.example.absolutecinema.data.model.response.MovieDetailsRemoteDataModel
import com.example.absolutecinema.domain.mapper.GenreDomainMapper.toDomain

object MovieDetailsDomainMapper :
    BaseDomainMapper<MovieDetailsRemoteDataModel, MovieDetailsDomainModel> {
    override fun MovieDetailsRemoteDataModel.toDomain() = MovieDetailsDomainModel(
        backdropPath = backdropPath,
        genres = genres.map { it.toDomain() },
        id = id,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        runtime = runtime,
        status = status,
        title = title,
        video = video,
        voteAverage = voteAverage
    )

    override fun MovieDetailsDomainModel.toData(): MovieDetailsRemoteDataModel {
        throw Exception("Not implemented")
    }
}