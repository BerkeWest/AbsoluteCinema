package com.example.absolutecinema.ui.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.data.remote.model.request.MovieSearchResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WatchListViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(WatchListUIState())
    val uiState: StateFlow<WatchListUIState> = _uiState.asStateFlow()

    init {
        loadWatchList()
    }

    fun loadWatchList() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val watchList = repository.getWatchList()
                val filteredResult = watchList.filter { !it.poster_path.isNullOrBlank() }.map { movieResult ->
                    val genreNames = repository.getGenreNamesByIds(movieResult.genre_ids)
                    movieResult.copy(genre = genreNames)
                }
                _uiState.update { it.copy(isLoading = false, watchlist = filteredResult) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, watchlist = emptyList()) }
            }
        }
    }

}

data class WatchListUIState(
    val isLoading: Boolean = true,
    val watchlist: List<MovieSearchResult> = emptyList()
)