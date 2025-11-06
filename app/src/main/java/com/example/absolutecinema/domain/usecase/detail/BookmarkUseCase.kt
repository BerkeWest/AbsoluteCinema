package com.example.absolutecinema.domain.usecase.detail

import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.presentation.detail.DetailUIState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookmarkUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(uiState: DetailUIState) = flow {
        val currentlyBookmarked = uiState.movieState?.watchlist == true
        repository.addToWatchlist(uiState.movieDetails?.id ?: 0, !currentlyBookmarked)
        emit(!currentlyBookmarked)
    }
}