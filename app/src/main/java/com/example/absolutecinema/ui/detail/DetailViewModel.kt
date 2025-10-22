package com.example.absolutecinema.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.data.remote.model.response.MovieDetails
import com.example.absolutecinema.data.remote.model.response.MovieState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUIState())
    val uiState: StateFlow<DetailUIState> = _uiState.asStateFlow()

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    init {
        loadDetails()
    }

    private fun loadDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val movieDetails = repository.getDetails(movieId)
                val movieState = repository.getMovieState(movieId)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        movieDetails = movieDetails,
                        movieState = movieState
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun bookmark() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                if (_uiState.value.movieState?.watchlist == true) {
                    repository.addToWatchlist(movieId, false)
                } else if (_uiState.value.movieState?.watchlist == false) {
                    repository.addToWatchlist(movieId, true)
                }
                _uiState.update {
                    it.copy(movieState = it.movieState?.copy(watchlist = !(_uiState.value.movieState?.watchlist!!)), isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

}

data class DetailUIState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null,
    val movieState: MovieState? = null
)
