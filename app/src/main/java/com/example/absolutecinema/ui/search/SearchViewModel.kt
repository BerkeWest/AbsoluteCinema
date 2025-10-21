package com.example.absolutecinema.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.data.movie.MovieRepository
import com.example.absolutecinema.data.remote.model.request.SearchItem
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchResults = MutableStateFlow<List<SearchItem>>(emptyList())
    val searchResults = _searchResults.asStateFlow()


    init {
        viewModelScope.launch {
            searchText
                .debounce(500L)
                .distinctUntilChanged()
                .collect { text ->
                    if (text.isNotBlank()) {
                        searchMovies(text)
                    } else {
                        _searchResults.value = emptyList()
                    }
                }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    private suspend fun searchMovies(word: String) {
        _isSearching.value = true
        try {
            val result = repository.search(word)
            Log.d("SearchViewModel", "Search for '$word' returned ${result.size} results")
            val filteredResult = result.filter { !it.poster_path.isNullOrBlank() }
            Log.d("SearchViewModel", "After filtering: ${filteredResult.size} results")
            _searchResults.value = filteredResult
        } catch (e: Exception) {
            Log.e("SearchViewModel", "Search error", e)
            _searchResults.value = emptyList()
        } catch (e: Exception) {
            _searchResults.value = emptyList()
        } finally {
            _isSearching.value = false
        }
    }


}