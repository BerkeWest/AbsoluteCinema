package com.example.absolutecinema.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.R
import com.example.absolutecinema.base.onError
import com.example.absolutecinema.base.onSuccess
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import com.example.absolutecinema.domain.usecase.home.LoadTopMoviesUseCase
import com.example.absolutecinema.domain.usecase.home.OnTabSelectedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val loadTopMoviesUseCase: LoadTopMoviesUseCase,
    private val onTabSelectedUseCase: OnTabSelectedUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val tabs = listOf(R.string.now_playing, R.string.popular, R.string.upcoming, R.string.top_rated)


    init {
        loadTopMovies()
        onTabSelected(0)
    }

    private fun loadTopMovies() {
        showLoading()
        loadTopMoviesUseCase.invoke(FlowUseCase.Params())
            .onSuccess { data ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        topMovies = data
                    )
                }
            }.onError { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        snackBarMessage = error.localizedMessage
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun showLoading() {
        _uiState.update { it.copy(isLoading = true) }
    }

    fun onTabSelected(tabIndex: Int) {
        onTabSelectedUseCase.invoke(OnTabSelectedUseCase.Params(tabIndex))
            .onSuccess { result ->
                _uiState.update {
                    it.copy(
                        selectedTabIndex = tabIndex,
                        tabResult = result
                    )
                }
            }.onError { error ->
                _uiState.update {
                    it.copy(
                        snackBarMessage = error.localizedMessage
                    )
                }
            }.launchIn(viewModelScope)
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val snackBarMessage: String? = null,
    val topMovies: List<MovieSearchResultDomainModel> = emptyList(),
    val selectedTabIndex: Int = 0,
    val tabResult: List<MovieSearchResultDomainModel> = emptyList()
)