package com.example.absolutecinema.domain.mapper

import com.example.absolutecinema.domain.base.BaseDomainMapper
import com.example.absolutecinema.data.model.response.MovieStateDomainModel
import com.example.absolutecinema.data.model.response.MovieStateRemoteDataModel

object MovieStateDomainMapper :
    BaseDomainMapper<MovieStateRemoteDataModel, MovieStateDomainModel> {
    override fun MovieStateRemoteDataModel.toDomain() = MovieStateDomainModel(
        id = id,
        favorite = favorite,
        rated = rated,
        watchlist = watchlist
    )

    override fun MovieStateDomainModel.toData(): MovieStateRemoteDataModel {
        throw Exception("Not implemented")
    }
}