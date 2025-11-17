package com.example.absolutecinema.domain.usecase.detail

import com.example.absolutecinema.data.model.response.CastDomainModel
import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.di.IoDispatcher
import com.example.absolutecinema.domain.mapper.MovieCastDomainMapper.toDomain
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map

class LoadCastUseCase @Inject constructor(
    private val repository: MovieRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<LoadCastUseCase.Params, List<CastDomainModel>>(
    dispatcher
) {

    override fun execute(params: Params) = repository.getCast(params.movieId)
        .map { resultPage -> resultPage.take(30).map { it.toDomain() } }

    data class Params(
        val movieId: Int
    ) : FlowUseCase.Params()
}