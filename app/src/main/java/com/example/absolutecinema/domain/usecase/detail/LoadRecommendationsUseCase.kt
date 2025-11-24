package com.example.absolutecinema.domain.usecase.detail

import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.di.IoDispatcher
import com.example.absolutecinema.domain.mapper.MovieSearchResultDomainMapper.toDomain
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadRecommendationsUseCase @Inject constructor(
    private val repository: MovieRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<LoadRecommendationsUseCase.Params, List<MovieSearchResultDomainModel>>(dispatcher) {

    override fun execute(params: Params) = repository.getRecommendations(params.movieId)
        .map { resultPages -> resultPages.results.map { it.toDomain() } }

    data class Params(
        val movieId: Int
    ) : FlowUseCase.Params()
}