package com.example.absolutecinema.domain.usecase.detail

import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.di.IoDispatcher
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkUseCase @Inject constructor(
    private val repository: MovieRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<BookmarkUseCase.Params, Boolean>(dispatcher) {

    override fun execute(params: Params): Flow<Boolean> {
        return repository.addToWatchlist(params.movieId, !params.state)
            .map { !params.state }
    }

    data class Params(
        val state: Boolean, val movieId: Int
    ) : FlowUseCase.Params()
}