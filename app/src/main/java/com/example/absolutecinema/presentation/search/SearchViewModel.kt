package com.example.absolutecinema.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.absolutecinema.domain.base.onError
import com.example.absolutecinema.domain.base.onLoading
import com.example.absolutecinema.domain.base.onSuccess
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.domain.usecase.home.OnTabSelectedUseCase
import com.example.absolutecinema.domain.usecase.search.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUIState())
    val uiState: StateFlow<SearchUIState> = _uiState.asStateFlow()

    val moviesPagingFlow: Flow<PagingData<MovieSearchResultDomainModel>> = _uiState
        .map { it.searchText }
        .distinctUntilChanged()
        .flatMapLatest { searchText ->
            searchUseCase.invoke(SearchUseCase.Params(searchText))
        }
        .cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            uiState.map { it.searchText }
                .debounce(500L)
                .distinctUntilChanged()
                .collect { text ->
                    if (text.isNotBlank()) {
                        delay(100L)
                        searchMovies(text)
                    } else {
                    }
                }
        }
    }

    fun onSearchTextChange(text: String) {
        _uiState.update { currentState ->
            currentState.copy(searchText = text, searchAttempted = false)
        }
    }

    private fun searchMovies(word: String) {
    }
}

data class SearchUIState(
    val snackBarMessage: String? = null,
    val searchText: String = "",
    val isSearching: Boolean = false,
    val searchAttempted: Boolean = false
)