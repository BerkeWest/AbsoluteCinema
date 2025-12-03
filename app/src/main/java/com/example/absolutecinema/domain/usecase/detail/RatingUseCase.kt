package com.example.absolutecinema.domain.usecase.detail

import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.di.IoDispatcher
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RatingUseCase @Inject constructor(
    private val repository: MovieRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<RatingUseCase.Params, Boolean>(dispatcher) {

    override fun execute(params: Params) = when (params.rating) {
        null -> repository.deleteRating(params.movieId)
        else -> repository.rateMovie(params.movieId, params.rating)
    }.map { it == Unit }

    data class Params(
        val movieId: Int,
        val rating: Float?,
    ) : FlowUseCase.Params()
}