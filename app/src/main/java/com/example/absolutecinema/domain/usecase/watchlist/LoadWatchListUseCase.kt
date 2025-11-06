package com.example.absolutecinema.domain.usecase.watchlist

import com.example.absolutecinema.data.movie.MovieRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadWatchListUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke() = flow {
        val watchList = repository.getWatchList()
        val filteredResult =
            watchList.filter { !it.poster_path.isNullOrBlank() }.map { movieResult ->
                val genreNames = repository.getGenreNamesByIds(movieResult.genre_ids)
                movieResult.copy(genre = genreNames)
            }
        emit(filteredResult)
    }
}