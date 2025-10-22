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

    private fun loadWatchList(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val watchList = repository.getWatchList()
                _uiState.update { it.copy(isLoading = false, watchlist = watchList) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

}

data class WatchListUIState(
    val isLoading: Boolean = true,
    val watchlist: List<MovieSearchResult> = emptyList()
)