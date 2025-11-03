package com.example.absolutecinema.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.data.remote.model.response.MovieDetails
import com.example.absolutecinema.data.remote.model.response.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: MovieRepository
) : ViewModel() {

    //Değiştirilebilir UI state
    private val _uiState = MutableStateFlow(DetailUIState())
    //UI için read. only UI state verisi
    val uiState: StateFlow<DetailUIState> = _uiState.asStateFlow()
    //navigation graphden gelen idyi çekerek kaydeder.
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
            //avoids double click while loading
            if (_uiState.value.isLoading) return@launch

            _uiState.update { it.copy(isLoading = true) }
            try {
                val currentState = _uiState.value.movieState
                val currentlyBookmarked = currentState?.watchlist == true
                repository.addToWatchlist(_uiState.value.movieDetails?.id ?: 0, !currentlyBookmarked)

                _uiState.update {
                    it.copy(movieState = it.movieState?.copy(watchlist = !currentlyBookmarked), isLoading = false)
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
