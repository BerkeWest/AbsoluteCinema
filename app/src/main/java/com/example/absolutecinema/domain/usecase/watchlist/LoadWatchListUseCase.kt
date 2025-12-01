package com.example.absolutecinema.domain.usecase.watchlist

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoadWatchListUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    operator fun invoke(): Flow<PagingData<MovieSearchResultDomainModel>> {
        return repository.getWatchList()
            .map { pagingData ->
                pagingData
                    .filter { !it.posterPath.isNullOrBlank() }
                    .map { movie ->
                        movie.copy(
                            genre = repository.getGenreNamesByIds(movie.genreIds)
                        )
                    }
            }
    }
}