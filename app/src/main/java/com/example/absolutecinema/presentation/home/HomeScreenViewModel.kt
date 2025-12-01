package com.example.absolutecinema.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.absolutecinema.R
import com.example.absolutecinema.domain.base.onError
import com.example.absolutecinema.domain.base.onSuccess
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import com.example.absolutecinema.domain.usecase.home.LoadTopMoviesUseCase
import com.example.absolutecinema.domain.usecase.home.OnTabSelectedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModel @Inject constructor(
    private val onTabSelectedUseCase: OnTabSelectedUseCase,
    private val loadTopMoviesUseCase: LoadTopMoviesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val tabs = listOf(R.string.now_playing, R.string.upcoming, R.string.top_rated, R.string.popular)

    val moviesPagingFlow: Flow<PagingData<MovieSearchResultDomainModel>> = _uiState
        .map { it.selectedTabIndex }
        .distinctUntilChanged()
        .flatMapLatest { index ->
            onTabSelectedUseCase.invoke(OnTabSelectedUseCase.Params(index))
        }
        .cachedIn(viewModelScope)

    init {
        loadTopMovies()
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
                        snackBarMessage = error.localizedMessage,
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun showLoading() {
        _uiState.update { it.copy(isLoading = true) }
    }

    fun onTabSelected(tabIndex: Int) {
        _uiState.update { it.copy(selectedTabIndex = tabIndex) }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val snackBarMessage: String? = null,
    val selectedTabIndex: Int = 0,
    val topMovies: List<MovieSearchResultDomainModel> = emptyList(),
)