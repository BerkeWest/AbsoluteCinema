package com.example.absolutecinema.domain.mapper

import com.example.absolutecinema.base.BaseDomainMapper
import com.example.absolutecinema.data.model.response.CastDomainModel
import com.example.absolutecinema.data.model.response.CastRemoteDataModel

object MovieCastDomainMapper :
    BaseDomainMapper<CastRemoteDataModel, CastDomainModel> {
    override fun CastRemoteDataModel.toDomain() = CastDomainModel(
        name = name,
        profilePath = profilePath,
        character = character
    )

    override fun CastDomainModel.toData(): CastRemoteDataModel {
        throw Exception("Not implemented")
    }
}