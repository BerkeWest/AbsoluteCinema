package com.example.absolutecinema.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.data.model.response.MovieSearchResult
import com.example.absolutecinema.domain.usecase.home.LoadTopMoviesUseCase
import com.example.absolutecinema.domain.usecase.home.OnTabSelectedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val loadTopMoviesUseCase: LoadTopMoviesUseCase,
    private val onTabSelectedUseCase: OnTabSelectedUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val tabs = listOf("Now Playing", "Upcoming", "Top rated", "Popular")


    init {
        loadTopMovies()
        onTabSelected(0)
    }

    private fun loadTopMovies() {
        viewModelScope.launch {
            loadTopMoviesUseCase().collect { result ->
                _uiState.update { it.copy(topMovies = result) }
            }
        }
    }

    fun onTabSelected(tabIndex: Int) {
        viewModelScope.launch {
            onTabSelectedUseCase(tabIndex).collect { movies ->
                _uiState.update { it.copy(selectedTabIndex = tabIndex, tabResult = movies) }
            }
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val topMovies: List<MovieSearchResult> = emptyList(),
    val selectedTabIndex: Int = 0,
    val tabResult: List<MovieSearchResult> = emptyList()
)