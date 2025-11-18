package com.example.absolutecinema.domain.mapper

import com.example.absolutecinema.base.BaseDomainMapper
import com.example.absolutecinema.data.model.response.AuthorDomainModel
import com.example.absolutecinema.data.model.response.AuthorRemoteDataModel


object AuthorDomainMapper :
    BaseDomainMapper<AuthorRemoteDataModel, AuthorDomainModel> {
    override fun AuthorRemoteDataModel.toDomain() = AuthorDomainModel(
        name = name,
        username = username,
        avatarPath = avatarPath,
        rating = rating
    )

    override fun AuthorDomainModel.toData(): AuthorRemoteDataModel {
        throw Exception("Not implemented")
    }
}