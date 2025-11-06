package com.example.absolutecinema.domain.usecase.detail

import com.example.absolutecinema.data.movie.MovieRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.flow

class LoadDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int) = flow {
        val movieDetails = repository.getDetails(movieId)
        val movieState = repository.getMovieState(movieId)
        emit(movieDetails to movieState)
    }
}