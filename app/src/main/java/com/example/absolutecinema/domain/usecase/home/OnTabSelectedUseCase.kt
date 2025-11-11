package com.example.absolutecinema.domain.usecase.home

import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.di.IoDispatcher
import com.example.absolutecinema.domain.mapper.MovieSearchResultDomainMapper.toDomain
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OnTabSelectedUseCase @Inject constructor(
    private val repository: MovieRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<OnTabSelectedUseCase.Params, List<MovieSearchResultDomainModel>>(dispatcher) {

    override fun execute(params: Params) = when (params.tabIndex) {
        0 -> repository.getNowPlaying()
            .map { resultPages -> resultPages.results.map { it.toDomain() } }

        1 -> repository.getUpcoming()
            .map { resultPages -> resultPages.results.map { it.toDomain() } }

        2 -> repository.getTopRated()
            .map { resultPages -> resultPages.results.map { it.toDomain() } }

        else -> repository.getPopular()
            .map { resultPages -> resultPages.results.map { it.toDomain() } }
    }

    data class Params(
        val tabIndex: Int
    ) : FlowUseCase.Params()
}
