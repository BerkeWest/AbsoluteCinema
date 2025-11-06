package com.example.absolutecinema.domain.usecase.home

import com.example.absolutecinema.data.model.response.ResultPages
import com.example.absolutecinema.data.movie.MovieRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnTabSelectedUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(tabIndex: Int) = flow{
        val movies = when (tabIndex) {
            0 -> repository.getNowPlaying()
            1 -> repository.getUpcoming()
            2 -> repository.getTopRated()
            3 -> repository.getPopular()
            else -> ResultPages(0, emptyList(), 0, 0)
        }
        emit(movies.results)
    }
}