package com.example.absolutecinema.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.data.model.response.MovieDetails
import com.example.absolutecinema.data.model.response.MovieState
import com.example.absolutecinema.domain.usecase.detail.BookmarkUseCase
import com.example.absolutecinema.domain.usecase.detail.LoadDetailsUseCase
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
    private val loadDetailsUseCase: LoadDetailsUseCase,
    private val bookmarkUseCase: BookmarkUseCase,
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
                loadDetailsUseCase(movieId).collect { (movieDetails, movieState) ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            movieDetails = movieDetails,
                            movieState = movieState
                        )
                    }
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
                bookmarkUseCase(_uiState.value).collect { newWatchlistStatus ->
                    _uiState.update {
                        it.copy(
                            movieState = it.movieState?.copy(watchlist = newWatchlistStatus),
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    fun getGenreString(genres: List<String>): String {
        return genres.joinToString(", ")
    }

}

data class DetailUIState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null,
    val movieState: MovieState? = null
)
