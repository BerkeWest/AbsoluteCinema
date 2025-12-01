package com.example.absolutecinema.domain.usecase.home

import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.di.IoDispatcher
import com.example.absolutecinema.domain.mapper.MovieSearchResultDomainMapper.toDomain
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadTopMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : FlowUseCase<FlowUseCase.Params, List<MovieSearchResultDomainModel>>(dispatcher) {

    override fun execute(params: Params) = repository.getTrending()
        .map { resultPage ->
            resultPage.results.take(10)
                .map { it.toDomain() }
        }
}