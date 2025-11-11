package com.example.absolutecinema.domain.mapper

import com.example.absolutecinema.base.BaseDomainMapper
import com.example.absolutecinema.data.model.response.GenreRemoteDataModel
import com.example.absolutecinema.domain.model.response.GenreDomainModel

object GenreDomainMapper :
    BaseDomainMapper<GenreRemoteDataModel, GenreDomainModel> {
    override fun GenreRemoteDataModel.toDomain() = GenreDomainModel(
        id = id,
        name = name
    )

    override fun GenreDomainModel.toData(): GenreRemoteDataModel {
        throw Exception("Not implemented")
    }
}