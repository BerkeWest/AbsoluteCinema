package com.example.absolutecinema.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.absolutecinema.domain.model.response.MovieSearchResultDomainModel
import com.example.absolutecinema.domain.usecase.search.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUIState())
    val uiState: StateFlow<SearchUIState> = _uiState.asStateFlow()

    val searchResultFlow: Flow<PagingData<MovieSearchResultDomainModel>> = _uiState
        .map { it.searchText }
        .distinctUntilChanged()
        .debounce(500L)
        .onEach { query ->
            if (query.isNotBlank()) {
                _uiState.update { it.copy(searchAttempted = true) }
            }
        }
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                searchUseCase.invoke(SearchUseCase.Params(query))
            }
        }
        .cachedIn(viewModelScope)

    fun onSearchTextChange(text: String) {
        _uiState.update {
            it.copy(searchText = text, searchAttempted = false)
        }
    }
}

data class SearchUIState(
    val snackBarMessage: String? = null,
    val searchText: String = "",
    val searchAttempted: Boolean = false
)