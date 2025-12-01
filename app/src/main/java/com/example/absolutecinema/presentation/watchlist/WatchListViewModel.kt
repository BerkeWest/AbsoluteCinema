package com.example.absolutecinema.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.domain.usecase.watchlist.LoadWatchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class WatchListViewModel @Inject constructor(
    private val loadWatchListUseCase: LoadWatchListUseCase
) : ViewModel() {

    val _uiState = MutableStateFlow(WatchListUiState())

    init {
        _uiState.update { it.copy(refresh = true) }
    }

    val moviesPagingFlow: Flow<PagingData<MovieSearchResultDomainModel>> =
        _uiState.map { it.refresh }
            .distinctUntilChanged()
            .flatMapLatest {
                loadWatchListUseCase.invoke()
            }
            .cachedIn(viewModelScope).also { _uiState.update { it.copy(refresh = false) } }

}

data class WatchListUiState(
    val refresh: Boolean = false
)