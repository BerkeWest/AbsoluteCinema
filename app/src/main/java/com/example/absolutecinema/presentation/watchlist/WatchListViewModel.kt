package com.example.absolutecinema.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.base.onError
import com.example.absolutecinema.base.onLoading
import com.example.absolutecinema.base.onSuccess
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import com.example.absolutecinema.domain.usecase.watchlist.LoadWatchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
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
        loadWatchListUseCase.invoke(FlowUseCase.Params())
            .onLoading {
                _uiState.update { it.copy(isLoading = true) }
            }
            .onSuccess { data ->
                _uiState.update { it.copy(isLoading = false, watchlist = data) }
            }
            .onError { error ->
                _uiState.update {
                    it.copy(
                        snackBarMessage = error.localizedMessage,
                        isLoading = false,
                        watchlist = emptyList()
                    )
                }
            }
            .launchIn(viewModelScope)
    }

}

data class WatchListUIState(
    val snackBarMessage: String? = null,
    val isLoading: Boolean = true,
    val watchlist: List<MovieSearchResultDomainModel> = emptyList()
)