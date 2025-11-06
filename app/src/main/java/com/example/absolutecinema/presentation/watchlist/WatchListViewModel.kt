package com.example.absolutecinema.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.data.model.response.MovieSearchResult
import com.example.absolutecinema.domain.usecase.watchlist.LoadWatchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val loadWatchListUseCase: LoadWatchListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WatchListUIState())
    val uiState: StateFlow<WatchListUIState> = _uiState.asStateFlow()

    init {
        loadWatchList()
    }

    fun loadWatchList() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                loadWatchListUseCase().collect { result ->
                    _uiState.update { it.copy(isLoading = false, watchlist = result) }
                }
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