package com.example.absolutecinema.domain.usecase.detail

import com.example.absolutecinema.data.model.response.ReviewResultDomainModel
import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.di.IoDispatcher
import com.example.absolutecinema.domain.mapper.MovieReviewsDomainMapper.toDomain
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map

class LoadReviewsUseCase @Inject constructor(
    private val repository: MovieRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<LoadReviewsUseCase.Params, List<ReviewResultDomainModel>>(
    dispatcher
) {

    override fun execute(params: Params) =
        repository.getReviews(params.movieId)
            .map { reviewsRemoteDataModel ->
                reviewsRemoteDataModel.toDomain().results
            }


    data class Params(
        val movieId: Int
    ) : FlowUseCase.Params()
}