package com.example.absolutecinema.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.data.remote.model.request.MovieSearchResult
import com.example.absolutecinema.data.remote.model.response.ResultPages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadTopMovies()
        loadMoviesForTab(0)
    }

    private fun loadTopMovies() {
        viewModelScope.launch {
            val result = repository.getPopular()
            _uiState.update { it.copy(topMovies = result.results.take(5)) }
        }
    }

    fun loadMoviesForTab(tabIndex: Int) {
        viewModelScope.launch {
            val movies = when (tabIndex) {
                0 -> repository.getNowPlaying()
                1 -> repository.getUpcoming()
                2 -> repository.getTopRated()
                3 -> repository.getPopular()
                else -> ResultPages(0,emptyList(),0,0)
            }
            _uiState.update { it.copy(tabResult = movies.results) }
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val topMovies: List<MovieSearchResult> = emptyList(),
    val tabResult: List<MovieSearchResult> = emptyList()
)