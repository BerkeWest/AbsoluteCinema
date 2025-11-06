package com.example.absolutecinema.domain.usecase.home

import com.example.absolutecinema.data.movie.MovieRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadTopMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke() = flow {
        val popular = repository.getPopular()
        emit(popular.results.take(10))
    }
}