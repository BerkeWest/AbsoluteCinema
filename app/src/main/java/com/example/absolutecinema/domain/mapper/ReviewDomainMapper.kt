package com.example.absolutecinema.domain.mapper

import com.example.absolutecinema.base.BaseDomainMapper
import com.example.absolutecinema.data.model.response.ReviewResultDomainModel
import com.example.absolutecinema.data.model.response.ReviewResultRemoteDataModel
import com.example.absolutecinema.domain.mapper.AuthorDomainMapper.toDomain


object ReviewDomainMapper :
    BaseDomainMapper<ReviewResultRemoteDataModel, ReviewResultDomainModel> {
    override fun ReviewResultRemoteDataModel.toDomain() = ReviewResultDomainModel(
        author = author,
        authorDetails = authorDetails.toDomain(),
        content = content
    )

    override fun ReviewResultDomainModel.toData(): ReviewResultRemoteDataModel {
        throw Exception("Not implemented")
    }
}