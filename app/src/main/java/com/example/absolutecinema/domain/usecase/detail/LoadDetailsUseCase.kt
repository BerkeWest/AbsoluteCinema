package com.example.absolutecinema.domain.usecase.detail

import com.example.absolutecinema.data.model.response.MovieDetailsDomainModel
import com.example.absolutecinema.data.model.response.MovieStateDomainModel
import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.di.IoDispatcher
import com.example.absolutecinema.domain.mapper.MovieDetailsDomainMapper.toDomain
import com.example.absolutecinema.domain.mapper.MovieStateDomainMapper.toDomain
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map


class LoadDetailsUseCase @Inject constructor(
    private val repository: MovieRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<LoadDetailsUseCase.Params, Pair<MovieDetailsDomainModel, MovieStateDomainModel>>(
    dispatcher
) {

    override fun execute(params: Params) =
        repository.getDetails(params.movieId).map { it.toDomain() }
            .combine(
                repository.getMovieState(params.movieId)
                    .map { it.toDomain() }) { details, state ->
                Pair(details, state)
            }

    data class Params(
        val movieId: Int
    ) : FlowUseCase.Params()
}