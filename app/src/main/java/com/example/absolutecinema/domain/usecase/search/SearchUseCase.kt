package com.example.absolutecinema.domain.usecase.search

import com.example.absolutecinema.data.movie.MovieRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.flow

class SearchUseCase @Inject constructor(
    private val repository: MovieRepository
){
    operator fun invoke(word: String) = flow {
        val result = repository.search(word)
        val filteredResult =
            result.filter { !it.poster_path.isNullOrBlank() }.map { movieResult ->
                val genreNames = repository.getGenreNamesByIds(movieResult.genre_ids)
                movieResult.copy(genre = genreNames)
            }
        emit(filteredResult)
    }
}