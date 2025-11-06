package com.example.absolutecinema.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.data.model.response.MovieSearchResult
import com.example.absolutecinema.domain.usecase.search.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class)
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUIState())
    val uiState: StateFlow<SearchUIState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            uiState.map { it.searchText }
                .debounce(500L)
                .distinctUntilChanged()
                .collect { text ->
                    if (text.isNotBlank()) {
                        searchMovies(text)
                    } else {
                        _uiState.update { it.copy(searchResults = emptyList()) }
                    }
                }
        }
    }

    fun onSearchTextChange(text: String) {
        _uiState.update { currentState ->
            currentState.copy(searchText = text, searchAttempted = false)
        }
    }

    private suspend fun searchMovies(word: String) {
        _uiState.update { it.copy(isSearching = true) }
        try {
            searchUseCase(word).collect { result ->
                _uiState.update {
                    it.copy(searchResults = result, isSearching = false, searchAttempted = true)
                }
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(searchResults = emptyList(), isSearching = false, searchAttempted = true)
            }
        }
    }
}

data class SearchUIState(
    val searchText: String = "",
    val isSearching: Boolean = false,
    val searchResults: List<MovieSearchResult> = emptyList(),
    val searchAttempted: Boolean = false
)