package com.example.absolutecinema.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.R
import com.example.absolutecinema.base.onError
import com.example.absolutecinema.base.onSuccess
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
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
    private val onTabSelectedUseCase: OnTabSelectedUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val tabs = listOf(R.string.now_playing, R.string.upcoming, R.string.top_rated, R.string.popular)


    init {
        _uiState.update { it.copy(isLoading = true) }
        getTabSelected(3)
        getTabSelected(0)
        _uiState.update { it.copy(isLoading = false, selectedTabIndex = 0) }
    }

    fun onTabSelected(tabIndex: Int) {
        when (tabIndex) {
            0 -> if (_uiState.value.nowPlaying.isEmpty()) getTabSelected(tabIndex)
            else _uiState.update { it.copy(selectedTabIndex = tabIndex) }
            1 -> if (_uiState.value.upcoming.isEmpty()) getTabSelected(tabIndex)
            else _uiState.update { it.copy(selectedTabIndex = tabIndex) }
            2 -> if (_uiState.value.topRated.isEmpty()) getTabSelected(tabIndex)
            else _uiState.update { it.copy(selectedTabIndex = tabIndex) }
            3 -> if (_uiState.value.popular.isEmpty()) getTabSelected(tabIndex)
            else _uiState.update { it.copy(selectedTabIndex = tabIndex) }
        }
    }

    fun getTabSelected(tabIndex: Int) {
        onTabSelectedUseCase.invoke(OnTabSelectedUseCase.Params(tabIndex))
            .onSuccess { result ->
                when (tabIndex) {
                    0 -> _uiState.update { it.copy(selectedTabIndex = tabIndex, nowPlaying = result) }
                    1 -> _uiState.update { it.copy(selectedTabIndex = tabIndex, upcoming = result) }
                    2 -> _uiState.update { it.copy(selectedTabIndex = tabIndex, topRated = result) }
                    3 -> _uiState.update { it.copy(selectedTabIndex = tabIndex, popular = result) }
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
    val selectedTabIndex: Int = 0,
    val nowPlaying: List<MovieSearchResultDomainModel> = emptyList(),
    val upcoming: List<MovieSearchResultDomainModel> = emptyList(),
    val topRated: List<MovieSearchResultDomainModel> = emptyList(),
    val popular: List<MovieSearchResultDomainModel> = emptyList(),
)