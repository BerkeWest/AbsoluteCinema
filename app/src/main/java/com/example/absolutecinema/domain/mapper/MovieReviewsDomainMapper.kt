package com.example.absolutecinema.domain.mapper

import com.example.absolutecinema.domain.base.BaseDomainMapper
import com.example.absolutecinema.data.model.response.ReviewsDomainModel
import com.example.absolutecinema.data.model.response.ReviewsRemoteDataModel
import com.example.absolutecinema.domain.mapper.ReviewDomainMapper.toDomain


object MovieReviewsDomainMapper :
    BaseDomainMapper<ReviewsRemoteDataModel, ReviewsDomainModel> {
    override fun ReviewsRemoteDataModel.toDomain() = ReviewsDomainModel(
        results = results.map { it.toDomain() }
    )

    override fun ReviewsDomainModel.toData(): ReviewsRemoteDataModel {
        throw Exception("Not implemented")
    }
}